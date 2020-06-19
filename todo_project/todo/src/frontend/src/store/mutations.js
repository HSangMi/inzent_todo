import {setAuthInHeader} from '../api'
const mutations = {
    LOGIN (state, token) {
        if(!token) return //토큰정보가 없으면, 리턴
        state.token = token // state에 토큰값 세팅
        localStorage.setItem('token',token) // 로컬스토리지에 token값 저장
        setAuthInHeader(token) // api의 requestHeader에 추가!
    },
    LOGOUT (state) {
        state.token = null
        delete localStorage.token
        setAuthInHeader(null)
    },
    SET_IS_SHOW_SUB_MENU (state, {activeMenu}) {
        if(state.activeMenu === activeMenu){
            state.activeMenu = undefined
            state.isShowSubMenu = false
        }else{
            state.activeMenu = activeMenu
            state.isShowSubMenu = true
        }
    },
    SET_ACTIVE_MENU (state, activeMenu) {
        state.activeMenu = activeMenu
    }

}

export default mutations