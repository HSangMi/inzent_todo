const state = {
  accessToken: null, // null, // 로그인처리 : 로그인 > 토큰값 저장 , 로그아웃 > 토큰값 삭제
  isShowSubMenu: false,
  activeMenu: "",
  userInfo: {},
  projects: [],
  project: {},
  headerTitle: "",
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
