import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    status: 'search',
    queryParam: ''
  },
  mutations: {
    edit (state, payload) {
      const {
        status,
        queryParam
      } = payload
      state.status = status
      state.queryParam = queryParam
    }
  }
})

export default store
