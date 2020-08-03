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
  todaySub: [],
  weekSub: [],
  ///////// SCHEDULE /////////
  isAddCalendar: false,
  isDetailCalendar: false,
  isDetailSub: false,
  clickDate: "",
  getClickDateList: [],
  getsubList: [],
  getSuperTitle: "",
  getProjectTitle: "",
  calendarList: [],
  getProjects: [],
  getSuperTasks: [],
  getFilter: [],
  getChkFilterItem: "",
  calImgCode: [],
  calFilterItem: [],
  ///////// ARCHIVE /////////
  archiveSuper: [],
  archiveSub: [],
};
export default state;
