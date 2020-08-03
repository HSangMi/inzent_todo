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
    state.userInfo = null;
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
  SET_COMMENTS(state, comments) {
    state.taskInfo.comments = comments;
  },
  //////////////////////// DASHBOARD ////////////////////////
  SET_TODAY_LIST(state, todayList) {
    state.todayList = todayList;
  },
  SET_TODAY_SUB(state, todaySub) {
    state.todaySub = todaySub;
  },
  SET_WEEK_LIST(state, weekList) {
    state.weekList = weekList;
  },
  SET_WEEK_SUB(state, weekSub) {
    state.weekSub = weekSub;
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
    clickDate.forEach((item) => {
      if (item.imgCode) {
        item.imgCode = JSON.parse(item.imgCode);
        item.managerName = JSON.parse(item.managerName);
      }
    });
    state.getClickDateList = clickDate;
  },
  SET_IS_DETAIL_SUB(state, toggle) {
    state.isDetailSub = toggle;
  },
  SET_FETCH_SUB_DETAIL(state, subList) {
    console.log("업무 소 조회....", subList);
    subList.forEach((item) => {
      if (item.imgCode) {
        item.imgCode = JSON.parse(item.imgCode);
        item.managerName = JSON.parse(item.managerName);
      }
    });
    state.getsubList = subList;
    state.getSuperTitle = subList[0].ptitle;
    state.getProjectTitle = subList[0].prjTitle;
  },
  SET_CALENDAR_LIST(state, calendarList) {
    state.calendarList = calendarList;
  },
  SET_FETCH_PROJECT(state, projectData) {
    state.getProjects = projectData;
  },
  SET_CHK_PROJECTINFO(state, projectData) {
    console.log("선택된 프로젝트 정보...", projectData);
    state.project = {
      id: projectData.project_id,
      memberNo: projectData.member_no,
    };
    state.labelList = projectData.labelVo;
    state.memberList = projectData.memberList;
  },
  SET_CHK_SUPERID(state, superId) {
    state.taskSuperId = superId;
  },
  SET_SUB_SORTNO(state, sortNo) {
    console.log("선택된 상위업무 정보...", sortNo);
    if (sortNo == "") {
      state.lastSubSortNo = 0;
    } else {
      state.lastSubSortNo = sortNo;
    }
  },
  SET_FETCH_CHKPROJECT(state, data) {
    state.getSuperTasks = data;
  },
  SET_FILTER(state, data) {
    state.getFilter = data;
  },
  SET_CHK_FITER_ITEM(state, data) {
    console.log("필터조회.......", data);
    state.getChkFilterItem = data;
  },
  SET_CALFILTER_ITEM(state, data) {
    const dataArr = data.split(",");

    state.calFilterItem = dataArr;
  },
  //////////////////////// ARCHIVE ////////////////////////
  SET_ARCHIVE_SUPER(state, data) {
    state.archiveSuper = data;
  },
  SET_ARCHIVE_SUB(state, data) {
    state.archiveSub = data;
  },
};

export default mutations;
