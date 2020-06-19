import Vue from 'vue'
import Vuex from 'vuex'
import state from './state'
import getters from './getters'
import mutations from './mutations'
import actions from './actions'

Vue.use(Vuex)

const store = new Vuex.Store({
      state,
      getters,
      mutations,
      actions
  })
// 어플리케이션이 구동되었을때, 브라우저 저장소에서 token이 있는지 확인하고 세팅해줌
//(자동 로그인)
const {token} = localStorage
store.commit('LOGIN',token)

export default store