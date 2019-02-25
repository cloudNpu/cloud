import {
    addMenu,
    deleteMenu,
    queryMenu,
    updateMenu
} from "@/services/first";

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
            yield put({
                type: "save",
                payload: response
            });
        },
        *add({ payload, callback }, { call, put }) {
            const response = yield (yield call(addMenu, payload)).json();
            yield put({
                type: "save",
                payload: response
            });
            //console.log(response);
            if (callback) callback();
        },
        *delete({ payload, callback }, { call, put }) {
            const response = yield (yield call(deleteMenu, payload)).json();
            //console.log(response);
            yield put({
                type: "save",
                payload: response
            });
            if (callback) callback();
        },
        *update({ payload, callback }, { call, put }) {
            const response = yield (yield call(updateMenu, payload)).json();
           // console.log(response);
            yield put({
                type: "save",
                payload: response
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
        }
    }
};
