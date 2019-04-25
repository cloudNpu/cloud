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
    },
    *delete({ payload, callback }, { call, put, select }) {
      // console.log(payload);
      const response = yield call(deleteMenu, payload);
      const list = yield select(state => state.first.data.list);
      //  console.log(list);  //删除前所有元素
      for (let i = 0, flag = true; i < list.length; flag ? i++ : i) {
        for (let j = 0; j < payload.id.length; j++) {
          if (list[i].id === payload.id[j]) {
            list.splice(i, 1);
            flag = false;
            break;
          } else {
            flag = true;
          }
        }
      }
      yield put({
        type: "save",
        payload: {
          list: list
        }
      });
      if (callback) callback();
    }
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
