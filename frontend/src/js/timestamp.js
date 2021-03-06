export const getDateFromDb = dateDB => {
  let year = dateDB.substr(0, 4)
  let month = dateDB.substr(5, 2)
  let day = dateDB.substr(8, 2)
  let hour1 = dateDB.substr(11, 2)
  let minute = dateDB.substr(14, 2)
  let second = dateDB.substr(17, 2)

  let tz = new Date();
  let hour = parseInt(hour1) - tz.getTimezoneOffset() / 60;
  day = parseInt(day);

  if (hour => 24) {
    hour = hour % 24;
    day += 1;
  }

  let time = hour.toString() + ':' + minute + ':' + second;
  let yearMonthDate = dateDB.substr(0, 10);

  let createdDate = new Date(year, month, day, hour, minute, second)
  let currentDate = new Date()

  let showDate = (currentDate.getDate() === createdDate.getDate()) ? 'Today at ' + time
      : (currentDate.getDate() - createdDate.getDate() === 1) ? 'Yesterday at ' + time
          : yearMonthDate + ' at ' + time

  return showDate
}