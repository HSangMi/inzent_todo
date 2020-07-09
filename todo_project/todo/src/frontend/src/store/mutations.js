import { setAuthInHeader } from '../api';
const mutations = {
	LOGIN(state, { accessToken }) {
		if (!accessToken) return; //토큰정보가 없으면, 리턴
		state.accessToken = accessToken; // state에 토큰값 세팅
		localStorage.setItem('accessToken', accessToken); // 로컬스토리지에 token값 저장
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
	SET_PROJECTS(state, projects) {
		console.log('mutations.SET_PROJECTS : 완료');
		state.projects = projects;
	},
	//////////////////////// DASHBOARD ////////////////////////
	SET_TODAY_LIST(state, todayList) {
		console.log(todayList);
		state.todayList = todayList;
	},
	SET_WEEK_LIST(state, weekList) {
		state.weekList = weekList;
	},
	SET_INTEREST_LIST(state, interestList) {
		state.interestList = interestList;
	}
};

export default mutations;
