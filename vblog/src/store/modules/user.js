import {
  login,
  logout
} from '@/api/login'
import {
  getToken,
  setToken,
  removeToken
} from '@/utils/auth'
import {
  setCookie,
  removeCookie
} from '@/utils/support'

const user = {
  state: {
    token: getToken(),
    username: '',
    avatar: '',
    roles: []
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token;
    },
    SET_USERNAME: (state, username) => {
      state.username = username;
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar;
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles;
    }
  },
  actions: {
    // 登录
    Login({
      commit
    }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo.username, userInfo.password)
          .then(response => {
            const data = response.data;
            const token = data.token;
            const roles = data.roles;
            const username = data.username;
            const avatar = data.avatar;
            setToken(token);
            setCookie('roles', roles, 1);
            commit('SET_TOKEN', token);
            commit('SET_ROLES', JSON.stringify(roles));
            commit('SET_USERNAME', username);
            commit('SET_AVATAR', avatar);
            resolve();
          })
          .catch(error => {
            reject(error);
          });
      });
    },
    // 登出
    LogOut({
      commit,
      state
    }) {
      return new Promise((resolve, reject) => {
        logout(state.token)
          .then(() => {
            commit('SET_TOKEN', '');
            commit('SET_USERNAME', []);
            removeToken();
            removeCookie('username');
            removeCookie('roles');
            resolve();
          })
          .catch(error => {
            reject(error);
          });
      });
    },

    // 前端 登出
    FedLogOut({
      commit
    }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '');
        removeToken();
        removeCookie('username');
        resolve();
      });
    }
  }
};

export default user
