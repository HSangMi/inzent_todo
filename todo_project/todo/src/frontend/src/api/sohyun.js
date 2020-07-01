/* 
    api 폴더 : Backend API를 호출하는 역할
    => axios 라이브러리에 대한 의존성을 낮추기위해, 
        모듈로 분리해서 관리
*/
import axios from "axios";
import router from "../routes"; // 상대경로 -> router/index.js

const DOMAIN = "http://localhost:8080/api"; //서버 요청 도메인
const UNAUTHORIZED = 401; //401에러에 대한 상수
const onUnauthorized = () => {
  // 401에러를 처리할 수 있는 함수
  router.push("/login");
};

const request = (method, url, data) => {
  return axios({
    method,
    url: DOMAIN + url,
    data,
  })
    .then((result) => result.data) // 성공일때, result의 body data를 전달
    .catch((result) => {
      const { status } = result.response;
      //401에러
      if (status === UNAUTHORIZED) return onUnauthorized();
      // 정의 되지 않은 에러
      throw Error(result);
    });
};
////////////////////////DASHBOARD/////////////////////////////////////
export const dashboard = {
    getTodayList() {
        return request("get", "/dashboard/today");
    },
    getWeekList() {
        return request("get", "/dashboard/week");
    },
    getInterestList() {
        return request("get", "/dashboard/starred");
    }
};
