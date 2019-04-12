import { addMenu, deleteMenu, queryMenu, updateMenu } from "@/services/first";

export default {
  namespace: "first",

  state: {
    data: {
      list: [],
      pagination: {}
    }
  },

  effects: {
    *fetch({ payload }, { call, put }) {
      const response = yield (yield call(queryMenu, payload)).json();
      //console.log(response);
      yield put({
        type: "save",
        payload: {
          list: response,
          pagination: {}
        }
      });
    },
    *add({ payload, callback }, { call, put, select }) {
      const response = yield (yield call(addMenu, payload)).json();
      yield put({
        type: "save",
        payload: {
          list: response
          //   pagination: {}
        }
      });
      if (callback) callback();
    },

    *update({ payload, callback }, { call, put }) {
      const response = yield (yield call(updateMenu, payload)).json();
      yield put({
        type: "save",
        payload: {
          list: response
          //   pagination: {}
        }
      });
      if (callback) callback();
    }
  },

  *delete({ payload, callback }, { call, put }) {
    const response = yield call(deleteMenu, payload);
    yield put({
      type: "save",
      payload: {
        list: response
        //   pagination: {}
      }
    });
    if (callback) callback();
  },

  reducers: {
    save(state, action) {
      return {
        ...state,
        data: action.payload
      };
      // console.log(state);
    }
  }
};
