import {
  addRole,
  deleteRole,
  queryRole,
  updateRole,
  menuList
} from "@/services/rolesearch";

export default {
  namespace: "rolesearch",

  state: {
    data: {
      list: [],
      pagination: {}
    }
    /*,
              menu: []*/
  },

  effects: {
    *fetch({ payload }, { call, put }) {
      const response = yield (yield call(queryRole, payload)).json();

      // for(let i=0;i<response.length;i++){
      //     for(let j=0;j<response.roleMenu.length;j++)
      //         response[i].roleMenu[j].push(",");
      // }
      //   console.log(response);
      yield put({
        type: "save",
        payload: {
          list: response
        }
      });
      // console.log(response);
    },
    *add({ payload, callback }, { call, put, select }) {
      const response = yield call(addRole, payload);
      //console.log(response);
      const list = yield select(state => state.rolesearch.data.list);
      list.push(payload);
      // console.log(list.map(item=>(item.id)));   遍历数组中的每一项的某一属性
      yield put({
        type: "save",
        payload: {
          list: list
          //   pagination: {}
        }
      });
      if (callback) callback();
    },

    *update({ payload, callback }, { call, put, select }) {
      const response = yield (yield call(updateRole, payload)).json();
      let list = yield select(state => state.rolesearch.data.list);
      //  console.log(list);
      yield list.forEach((value, index, array) => {
        let role = array[index];
        if (role.id === payload.id) {
          array[index] = payload;
        }
      });
      yield put({
        type: "save",
        payload: {
          list: list
          //   pagination: {}
        }
      });
      if (callback) callback();
    },

    *delete({ payload, callback }, { call, put }) {
      // console.log(payload);
      const response = yield (yield call(deleteRole, payload)).json();
      //  console.log(response);
      yield put({
        type: "save",
        payload: {
          list: response
        }
      });
      if (callback) callback();
    },

    *menu({ payload, callback }, { call, put, select }) {
      const response = yield (yield call(menuList, payload)).json();
      let list = yield select(state => state.rolesearch.data.list);
      // console.log(list);
      yield list.forEach((value, index, array) => {
        let role = array[index];
        // console.log(role);
        // console.log(array[index].roleMenu);
        if (role.name === response[0].name) {
          //  console.log(array[index].roleMenu);
          // let a=response[0].roleMenu;
          array[index].roleMenu.push(response[0].roleMenu);
        }
      });
      //   console.log(response);
      yield put({
        type: "save",
        payload: {
          list: list
          //   pagination: {}
        }
      });
    }
  },

  reducers: {
    save(state, action) {
      return {
        ...state,
        data: action.payload
      };
    } /*,
        menuList(state, action) {
          return {
            ...state,
            menu: action.payload
          };
        }*/
  }
};
