import { query as queryUsers, queryCurrent } from "@/services/center";
import { updateCenter } from "../services/center";
import user from "./user";

export default {
  namespace: "center",

  state: {
    list: [],
    currentUser: {
      /* key: "001",
      username:sessionStorage.getItem('user'.id),*/
    }
  },
  /*id: sessionStorage.getItem(user.id),
    username: sessionStorage.getItem(user.username)*/
  /*key: "001",
    username: "张三",
    dept: "技术部",
    mobile: 13366668888,
    avatar:
      "https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png",
    officeTel: "6666-4796521",
    new_passward: "",
    new_passward_again: ""*/
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
      yield put({
        type: "save",
        payload: response
      });
      if (callback) callback();
    },
    *fetchCurrent(_, { call, put }) {
      console.log("aaaaaa");
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
