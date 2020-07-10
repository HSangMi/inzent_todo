import { setAuthInHeader } from "../api";
const mutations = {
  LOGIN(state, { accessToken }) {
    if (!accessToken) return; //토큰정보가 없으면, 리턴
    state.accessToken = accessToken; // state에 토큰값 세팅
    localStorage.setItem("accessToken", accessToken); // 로컬스토리지에 token값 저장
    setAuthInHeader(accessToken); // api의 requestHeader에 추가!
  },
  LOGOUT(state) {
    state.accessToken = null;
    delete localStorage.accessToken;
    setAuthInHeader(null);
  },
  // SET_IS_SHOW_SUB_MENU(state, { activeMenu }) {
  // 	if (state.activeMenu === activeMenu) {
  // 		state.activeMenu = undefined;
  // 		state.isShowSubMenu = false;
  // 	} else {
  // 		state.activeMenu = activeMenu;
  // 		state.isShowSubMenu = true;
  // 	}
  // },
  //////////////////////// USER ////////////////////////
  SET_USER_INFO(state, userInfo) {
    state.userInfo = userInfo;
  },
  SET_DEPTLIST(state, deptList) {
    state.deptList = deptList;
  },
  SET_USERLIST(state, userList) {
    state.userList = userList;
  },
  ////////////////////////  ////////////////////////
  SET_ACTIVE_MENU(state, activeMenu) {
    state.activeMenu = activeMenu;
  },
  SET_HEADER_TITLE(state, string) {
    state.headerTitle = string;
  },
  /////////////////////PROJECT///////////////////////////////
  SET_PROJECTS(state, projects) {
    console.log("mutations.SET_PROJECTS : 완료");
    state.projects = projects;
  },
  SET_PROJECT(state, project) {
    state.project = project;
  },
  SET_TASK_LIST(state, taskList) {
    state.taskList = taskList;
  },
  SET_ADD_SUB_TASK_MODAL(state, toggle) {
    state.addSubTaskModal = toggle;
  },
  SET_ADD_TASK_MODAL(state, toggle) {
    state.addTaskModal = toggle;
  },
  SET_SUPER_TASK_ID(state, taskId) {
    state.taskSuperId = taskId;
  },
  SET_LAST_SUB_SORT_NO(state, sortNo) {
    state.lastSubSortNo = sortNo;
  },
  SET_LABEL_LIST(state, labelList) {
    state.labelList = labelList;
  },
  SET_MEMBER_LIST(state, memberList) {
    state.memberList = memberList;
  },
  SET_TASK_INFO(state, taskInfo) {
    state.taskInfo = taskInfo;
  },
  //////////////////////// DASHBOARD ////////////////////////
  SET_TODAY_LIST(state, todayList) {
    state.todayList = todayList;
  },
  SET_WEEK_LIST(state, weekList) {
    state.weekList = weekList;
  },
  SET_STARRED_LIST(state, starredList) {
    state.starredList = starredList;
  },
  //////////////////////// SCHEDULE ////////////////////////
  SET_IS_ADD_CALENDAR(state, toggle) {
    state.isAddCalendar = toggle; // toggle : 불린형
  },
  SET_IS_DETAIL_CALENDAR(state, toggle) {
    state.isDetailCalendar = toggle;
  },
  IS_CLICK_DATE(state, focus) {
    state.clickDate = focus;
  },
  SET_FETCH_CLICKDATE(state, clickDate) {
    state.getClickDateList = clickDate;
  },
  SET_CALENDAR_LIST(state, calendarList) {
    state.calendarList = calendarList;
  },
  SET_FETCH_CHKPROJECT(state, data) {
    state.getSuperTasks = data;
  },
};

export default mutations;
