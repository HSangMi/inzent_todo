import * as api from "../api";
import * as sohyun from "../api/sohyun";
// import * as util from "../utils/auth";
// import state from './state'
const actions = {
  LOGIN({ commit }, { id, password }) {
    // context객체중 commit만 받아옴, payload : 이메일, 비번
    return api.auth.login(id, password).then((data) => {
      const accessToken = data.accessToken;
      const loginUser = data.loginUser;
      if (loginUser.gender == "f") {
        loginUser.gender = "여성";
      } else if (loginUser.gender == "m") {
        loginUser.gender = "남성";
      } else {
        loginUser.gender = null;
      }

      console.log(accessToken);
      console.log(loginUser);
      commit("LOGIN", { accessToken });
      commit("SET_USER_INFO", loginUser);
    });
  },
  LOGIN_BY_TOKEN({ commit }, { accessToken }) {
    // context객체중 commit만 받아옴, payload : 이메일, 비번
    return api.auth.loginByToken(accessToken).then((data) => {
      // const accessToken = data.accessToken;
      console.log("------토큰으로 로그인-----");
      console.log(data);
      const loginUser = data;
      console.log(loginUser);
      if (data.gender == "f") {
        loginUser.gender = "여성";
      } else if (data.gender == "m") {
        loginUser.gender = "남성";
      } else {
        loginUser.gender = null;
      }
      console.log(loginUser);
      commit("LOGIN", { accessToken });
      commit("SET_USER_INFO", loginUser);
    });
  },
  PWD_CHECK(_, { id, password }) {
    return api.user.pwdCheck(id, password);
  },
  FATCH_DEPTLIST({ commit }) {
    return api.user.fatch_dept().then((data) => {
      console.log("action: FATCH_DEPTLIST 완료");
      console.log(data);
      commit("SET_DEPTLIST", data);
    });
  },
  FATCH_USERLIST({ commit }, deptList) {
    return api.user.fatch_user(deptList).then((data) => {
      console.log("action: FATCH_USERLIST 완료");
      console.log(data);
      commit("SET_USERLIST", data);
    });
  },
  ////////////////////////////// PROJECT////////////////////////////////
  ADD_PROJECT(_, newProject) {
    // console.log("actions.ADD_PROJECT : ");
    return api.project.addProject(newProject).then((data) => data);
  },
  FETCH_PROJECTS({ commit }) {
    return api.project.fetch().then((data) => {
      console.log("actions: FETCH_PROJECTS 완료");
      console.log(data);
      commit("SET_PROJECTS", data);
    });
  },
  FETCH_PROJECT({ commit }, pid) {
    return api.project.fetch(pid).then((data) => {
      console.log("actions: FETCH_PROJECT 완료");
      data.project["memberNo"] = data.memberNo;
      console.dir(data);

      commit("SET_PROJECT", data.project);
      commit("SET_TASK_LIST", data.taskBoardList);
      commit("SET_LABEL_LIST", data.labelList);
      commit("SET_MEMBER_LIST", data.memberList);
    });
  },
  ADD_SUPER_TASK({ state, dispatch }, superTask) {
    console.log("actions.ADD_SUPER_TASK : ");
    return api.project
      .addSuperTask(superTask)
      .then(() => dispatch("FETCH_PROJECT", state.project.id));
  },
  ADD_SUB_TASK({ state, dispatch }, subTask) {
    console.log("actions.ADD_SUB_TASK : ");
    return api.project
      .addSubTask(subTask)
      .then(() => dispatch("FETCH_PROJECT", state.project.id));
  },
  ADD_NEW_LABEL(_, newLabel) {
    console.log(newLabel);
    return api.project.addNewLabel(newLabel).then(() => {});
  },
  FETCH_LABEL({ commit }, pid) {
    return api.project.fetchLabel(pid).then((data) => {
      console.dir(data);
      commit("SET_LABEL_LIST", data);
    });
  },
  FETCH_TASK({ commit }, taskId) {
    return api.project.fetchTask(taskId).then((data) => {
      console.dir(data);
      commit("SET_TASK_INFO", data);
    });
  },
  REORDER_TASK({ commit }, targetTask) {
    console.log("reorderTask!!!");
    console.log(targetTask);
    return api.project.reorderTask(targetTask).then((data) => {
      // console.log("리오더 끝!");
      // console.log(data);
      commit("SET_TASK_LIST", data);
    });
  },
  //////////////////////// DASHBOARD ////////////////////////
  FETCH_TODAY_DASHBOARD({ commit }) {
    //오늘 리스트
    return sohyun.dashboard
      .getTodayList()
      .then((result) => {
        // api에 전달
        commit("SET_TODAY_LIST", result);
      })
      .catch((err) => {
        console.log(err);
      });
  },
  FETCH_WEEK_DASHBOARD({ commit }) {
    // 주간 리스트
    return sohyun.dashboard
      .getWeekList()
      .then((result) => {
        commit("SET_WEEK_LIST", result);
      })
      .catch((err) => {
        console.log(err);
      });
  },
  FETCH_STARRED_DASHBOARD({ commit }) {
    // 관심 리스트
    return sohyun.dashboard
      .getInterestList()
      .then((result) => {
        commit("SET_STARRED_LIST", result);
      })
      .catch((err) => {
        console.log(err);
      });
  },
  //////////////////////// SCHEDULE (CALENDAR) ////////////////////////
  FETCH_CALENDAR_LIST({ commit }) {
    // 캘린더 조회
    return sohyun.schedule
      .getCalendarList()
      .then((result) => {
        commit("SET_CALENDAR_LIST", result);
      })
      .catch((err) => {
        console.log(err);
      });
  },
  FETCH_CALENDAR_SUPER_TASKS({ commit }, chkProject) {
    // 선택한 프로젝트의 업무 대 조회
    return sohyun.schedule
      .getChkProject(chkProject)
      .then((result) => {
        commit("SET_FETCH_CHKPROJECT", result);
      })
      .catch((err) => {
        console.log(err);
      });
  },
  ADD_CALENDAR_SUPER_TASKS(__, addSuper) {
    // 업무 대 추가
    return sohyun.schedule
      .addSuperTask(addSuper)
      .then((result) => result)
      .catch((err) => {
        console.log(err);
      });
  },
  ADD_CALENDAR_SUB_TASKS(__, addSub) {
    // 업무 소 추가
    return sohyun.schedule
      .addSubTask(addSub)
      .then((result) => result)
      .catch((err) => {
        console.log(err);
      });
  },
  FETCH_CALENDAR_CLICKDATE({ commit }, clickDate) {
    console.log("여기까지 들어오나요~~~~");
    // 해당날짜의 업무 조회
    return sohyun.schedule
      .getClickDateList(clickDate)
      .then((result) => {
        commit("SET_FETCH_CLICKDATE", result);
      })
      .catch((err) => {
        console.log(err);
      });
  },
};
export default actions;
