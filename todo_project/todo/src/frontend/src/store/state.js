const state = {
  accessToken: null, // null, // 로그인처리 : 로그인 > 토큰값 저장 , 로그아웃 > 토큰값 삭제
  isShowSubMenu: false,
  activeMenu: "",
  headerTitle: "",
  userInfo: {},
  ///// PROJECT////////////
  projects: [],
  project: {},
  addTaskModal: false,
  taskList: [],
  taskInfo: {},
  taskSuperId: undefined,
  lastSubSortNo: 0,
  labelList: [],
  memberList: [],
  /////////
  userList: [],
  deptList: [],
  ///////// DASHBOARD /////////
  todayList: [],
  weekList: [],
  starredList: [],
  ///////// SCHEDULE /////////
  isAddCalendar: false,
  isDetailCalendar: false,
  clickDate: "",
  getClickDateList: [],
  calendarList: [],
  getSuperTasks: [],
};
export default state;
