import { query as queryUsers, queryCurrent } from "@/services/user";

export default {
  namespace: "user",

  state: {
    list: [],
    currentUser: {}
  },

  effects: {
    *fetch(_, { call, put }) {
      const response =yield(yield call(queryUsers)).json();
      yield put({
        type: "save",
        payload: response
      });
    },
    *fetchCurrent(_, { call, put }) {
      //const response = yield(yield call(queryCurrent)).json();
     const response = sessionStorage.getItem("user");
     if (response == null || response == undefined) {
        yield (window.location.href = "/user/login");
      }
      yield put({
        type: "saveCurrentUser",
        payload: response
      });
    }
  },

  reducers: {
    save(state, action) {
      return {
        ...state,
        list: action.payload
      };
    },
    saveCurrentUser(state, action) {
      return {
        ...state,
        currentUser: action.payload || {}
      };
    },
    changeNotifyCount(state, action) {
      return {
        ...state,
        currentUser: {
          ...state.currentUser,
          notifyCount: action.payload
        }
      };
    }
  }
};
