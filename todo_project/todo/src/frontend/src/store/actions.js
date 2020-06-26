import * as api from '../api';
import * as util from '../utils/auth';
// import state from './state'
const actions = {
	LOGIN({ commit }, { id, password }) {
		// context객체중 commit만 받아옴, payload : 이메일, 비번
		return api.auth.login(id, password).then((data) => {
			const accessToken = data.accessToken;
			commit('LOGIN', { accessToken });
			commit('SET_USER_INFO', util.auth.parseJwt(accessToken));
		});
	}
};
export default actions;
