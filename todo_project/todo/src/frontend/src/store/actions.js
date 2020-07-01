import * as api from "../api";
import * as sohyun from "../api/sohyun";
import * as util from "../utils/auth";
// import state from './state'
const actions = {
  LOGIN({ commit }, { id, password }) {
    // context객체중 commit만 받아옴, payload : 이메일, 비번
    return api.auth.login(id, password).then((data) => {
      const accessToken = data.accessToken;
      commit("LOGIN", { accessToken });
      commit("SET_USER_INFO", util.auth.parseJwt(accessToken));
    });
  },
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
  }
};
export default actions;
