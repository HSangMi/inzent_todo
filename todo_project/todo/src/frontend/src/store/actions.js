import * as api from '../api'
// import state from './state'
const actions = {
    LOGIN ({commit}, {email, password}) { // context객체중 commit만 받아옴, payload : 이메일, 비번
        return api.auth.login(email, password) 
                .then(({accessToken})=>commit('LOGIN',accessToken))
                // 성공하면, 응답객체중 access만 가져온뒤, commit해줌(LOGIN 변이에 위임)
    },
}
export default actions