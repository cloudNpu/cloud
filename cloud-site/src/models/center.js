import { query as queryUsers, queryCurrent } from "@/services/center";
import { updateCenter } from "../services/center";
import user from "./user";

export default {
  namespace: "center",

  state: {
    list: [],
    currentUser: {}
  },
  effects: {
    *fetch(_, { call, put }) {
      const response = yield call(queryUsers);
      yield put({
        type: "save",
        payload: response
      });
    },
    *update({ payload, callback }, { call, put }) {
      console.log(payload);
      const response = yield (yield call(updateCenter, payload)).json();
        console.log(response);
        yield put({
        type: "save",
        payload: response
      });
      if (callback) callback();
    },
    *fetchCurrent(_, { call, put }) {
      const response = yield (yield call(queryCurrent)).json();
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
          notifyCount: action.payload,
          unreadCount: action.payload.unreadCount
        }
      };
    }
  }
};
