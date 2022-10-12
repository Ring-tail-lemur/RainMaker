let eventGuid = 0;
var today = new Date();
var y = today.getFullYear();
var m = today.getMonth();
var d = today.getDate();

export const INITIAL_EVENTS = [
  {
    id: createEventId(),
    title: "All Day Event",
    start: new Date(y, m, 1),
    className: "event-default",
  },
  {
    id: createEventId(),
    title: "Meeting",
    start: new Date(y, m, d - 1, 10, 30),
    allDay: false,
    className: "event-green",
  },
  {
    id: createEventId(),
    title: "Lunch",
    start: new Date(y, m, d + 7, 12, 0),
    end: new Date(y, m, d + 7, 14, 0),
    allDay: false,
    className: "event-red",
  },
  {
    id: createEventId(),
    title: "Md-pro Launch",
    start: new Date(y, m, d - 2, 12, 0),
    allDay: true,
    className: "event-azure",
  },
  {
    id: createEventId(),
    title: "Birthday Party",
    start: new Date(y, m, d + 1, 19, 0),
    end: new Date(y, m, d + 1, 22, 30),
    allDay: false,
    className: "event-azure",
  },
  {
    id: createEventId(),
    title: "Click for Creative Tim",
    start: new Date(y, m, 21),
    end: new Date(y, m, 22),
    url: "http://www.creative-tim.com/",
    className: "event-orange",
  },
  {
    id: createEventId(),
    title: "Click for Google",
    start: new Date(y, m, 21),
    end: new Date(y, m, 22),
    url: "http://www.creative-tim.com/",
    className: "event-orange",
  },
];

export function createEventId() {
  return String(eventGuid++);
}
