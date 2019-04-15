import {
  addFound,
  queryFound,
  removeFound,
  updateFound,
  Add_user_role,
  Add_user_app
} from "../services/found";

export default {
  namespace: "found",

  state: {
    data: {
      list: [],
      pagination: { pageSize: 8 }
    }
  },

  effects: {
    *fetch({ payload }, { call, put, select }) {
      const response = yield (yield call(queryFound, payload)).json();
      yield put({
        type: "save",
        payload: {
          list: response,
          pagination: { pageSize: 8 }
        }
      });
    },
    *add({ payload, callback }, { call, put, select }) {
      const response = yield call(addFound, payload);
      let list = yield select(state => state.found.data.list);
      list.push(payload.user);
      yield put({
        type: "save",
        payload: {
          list: list,
          pagination: { pageSize: 8 }
        }
      });
      if (callback) callback();
    },
    *remove({ payload, callback }, { call, put }) {
      const response = yield call(removeFound, payload);
      yield put({
        type: "save",
        payload: {
          list: response,
          pagination: { pageSize: 8 }
        }
      });
      // console.log(response);
      if (callback) callback();
    },
    *update({ payload, callback }, { call, put, select }) {
      const response = yield call(updateFound, payload);
      //let list = yield select(state => state.found.data.list);
      //console.log(list);
      // list.splice(payload.id-1,1,payload);
      // let user=payload.user;
      // console.log(user);
      //let index=payload.id-1;
      //console.log(index);
      // list.set(index,user);
      //  console.log(list);
      let list = yield select(state => state.found.data.list);
      yield put({
        type: "save",
        payload: {
          list: list, //response:undefine
          pagination: { pageSize: 8 }
        }
      });
      if (callback) callback();
    },
    *add_user_role({ payload, callback }, { call, put }) {
      console.log(payload);
      const response = yield call(Add_user_role, payload);
      yield put({
        type: "save",
        payload: {
          list: response,
          pagination: { pageSize: 8 }
        }
      });
      console.log(response);
      if (callback) callback();
    },
    *add_user_app({ payload, callback }, { call, put }) {
      const response = yield call(Add_user_app, payload);
      yield put({
        type: "save",
        payload: response
      });
      if (callback) callback();
    }
  },

  reducers: {
    //处理所有同步逻辑
    save(state, action) {
      return {
        ...state,
        data: action.payload //数据返回给页面
      };
    }
  }
};
