/* 
    api 폴더 : Backend API를 호출하는 역할
    => axios 라이브러리에 대한 의존성을 낮추기위해, 
        모듈로 분리해서 관리
*/
import axios from "axios";
import router from "../routes"; // 상대경로 -> router/index.js

const DOMAIN = "http://localhost:9000"; //서버 요청 도메인
const UNAUTHORIZED = 401; //401에러에 대한 상수
const onUnauthorized = () => {
  // 401에러를 처리할 수 있는 함수
  // /login?rPath=%2Flogin
  router.push(`/login?rPath=${encodeURIComponent(location.pathname)}`);
};

const request = (method, url, data) => {
  return axios({
    method,
    url: DOMAIN + url,
    data,
  })
    .then((res) => {
      return res.data;
    }) // 성공일때, result의 body data를 전달
    .catch((result) => {
      const { status } = result.response;
      //401에러
      if (status === UNAUTHORIZED) return onUnauthorized();
      // 정의 되지 않은 에러
      throw Error(result);
    });
};

/////////////////////////////////LOGIN////////////////////////////////////////
export const setAuthInHeader = (token) => {
  // 모든 request를 날리기 전에, Header값에 토큰 정보를 설정하는 메소드
  axios.defaults.headers.common["Authorization"] = token
    ? `Bearer ${token}`
    : null;
};
export const auth = {
  login(id, password) {
    return request("post", "/login", { id: id, password: password });
  },
};
/////////////////////////////////-- PROJECT --///////////////////////////////////////////
export const project = {
  // fetch(id){
  //     // get 메소드, 요청경로는 /boards
  //     // id가 있을경우엔 특정 보드 정보가져오기, 없을경우엔 전체 보드 목록 조회
  //     return id ? request('get',`/boards/${id}`) : request('get','/boards')
  // },
  addProject(project) {
    console.log(project);
    return request("post", "/projects/addProject", project);
  },
  fetch(id) {
    return id
      ? request("get", `/projects/${id}`)
      : request("get", "/projects/");
  },
};
