from bs4 import BeautifulSoup
import requests
import datetime
import json
import re
import io

# The JSON File will store a list of reservation objects
class Reservation:
    def __init__(self, eventName, room, time):
        self.room = room
        self.eventName = eventName
        self.time = time

# prepares timestamp to be interpolated into the master calender url to grab current data
def find_timestamp():
    now = datetime.datetime.now()
    current_year = str(now.year)
    current_month = str(now.month)
    if len(current_month) == 1:
        current_month = '0' + current_month
    current_day = str(now.day)
    if len(current_day) == 1:
        current_day = '0' + current_day
    print(current_year + current_month + current_day)
    return current_year + current_month + current_day

# takes timestamp and constructs url
def construct_url():
    timestamp = find_timestamp()
    link = 'https://mastercalendar.oxy.edu/wv34/wv_servlet/wrd/run/wv_space.DayList?spdt=' + timestamp + ',spfilter=8945,lbdviewmode=list'
    return link

# finds a list of all the rooms on master calendar
def get_rooms(soup):
    room_soup = soup.find_all("a", {"class": "ListText"})
    rooms = []
    for room in room_soup:
        rooms.extend(clean_list(room.get_text()))
    return rooms

# helper method to clean lists
def clean_list(text):
    return [line.strip() for line in text.splitlines() if line.strip()]

# takes a single row of the table, and finds all of the reservations for that room
def get_reservations(single_tr):
    time_res_soup = single_tr.find_all("td", {"class": "ListText"})
    room_soup = single_tr.find_all("a", {"class": "ListText"})
    if not room_soup or not time_res_soup:
        return []
    room = room_soup[0].get_text().strip()
    start_times = clean_list(time_res_soup[0].get_text())
    end_times = clean_list(time_res_soup[1].get_text())
    times = list(zip(start_times, end_times))
    events = clean_list(time_res_soup[2].get_text())
    reservations_for_room = []
    for i in range(len(events)):
        event = events[i]
        time_dict = {}
        start_time = times[i][0]
        end_time = times[i][1]
        time_dict['start_time'] = start_time
        time_dict['end_time'] = end_time
        reservation = Reservation(event, room, time_dict)
        reservations_for_room.append(reservation)
    return reservations_for_room

def main():
    url = construct_url()
    r = requests.get(url)
    data = r.text
    soup = BeautifulSoup(data, 'html.parser')
    reservations = []
    for tr in soup.find_all("tr"):
        reservations_in_room = get_reservations(tr)
        reservations.extend(reservations_in_room)

    def dict(obj):
        return obj.__dict__
    reservations = [dict(reservation) for reservation in reservations]
    reservation_dict = {}
    reservation_dict['reservations'] = reservations
    JSONdata = json.dumps(reservation_dict) # WRITE TO TEXT FILE
    with open("/Users/chloerainezeller/RoomFndr/app/src/main/assets/reservations.json", "w") as text_file:
        text_file.write(str(JSONdata))



if __name__ == "__main__":
    main()