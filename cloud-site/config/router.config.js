export default [
  // user
  {
    path: "/user",
    component: "../layouts/UserLayout",
    routes: [
      { path: "/user", redirect: "/user/login" },
      { path: "/user/login", component: "./User/Login" },
      { path: "/user/register", component: "./User/Register" },
      { path: "/user/register-result", component: "./User/RegisterResult" }
    ]
  },
  // app
  {
    path: "/",
    component: "../layouts/BasicLayout",
    Routes: ["src/pages/Authorized"],
    authority: ["admin", "user"],
    routes: [
      // dashboard
      { path: "/", redirect: "/dashboard/analysis" },
      {
        path: "/dashboard",
        name: "dashboard",
        icon: "dashboard",
        routes: [
          {
            path: "/dashboard/analysis",
            name: "analysis",
            component: "./Dashboard/Analysis"
          }
        ]
      },
      //role
      {
        path: "/role",
        icon: "meh",
        name: "role",
        routes: [
          {
            path: "/role/search",
            name: "search",
            component: "./Role/Search"
          }
        ]
      },
      // department
      {
        path: "/department",
        icon: "solution",
        name: "department",
        routes: [
          {
            path: "/department/department",
            name: "department",
            component: "./Department/Department"
          }
        ]
      },
      //APP
      {
        name: "app",
        icon: "smile",
        path: "/app",
        routes: [
          {
            path: "/app/search",
            name: "search",
            component: "./App/Search/AppList",
            routes: [
              {
                path: "/app/search",
                redirect: "/app/search/applist"
              }
            ]
          }
        ]
      },
      //users
      {
        name: "users",
        icon: "team",
        path: "/users",
        routes: [
          {
            path: "/users/found",
            name: "found",
            component: "./Users/Found"
          }
        ]
      },
      //My
      {
        name: "my",
        icon: "user",
        path: "/my",
        routes: [
          {
            path: "/my/center",
            name: "center",
            component: "./My/Center/Center",
            routes: [
              {
                path: "/my/center",
                component: "./My/Center/Center"
              }
            ]
          },
          {
            path: "/my/service",
            name: "service",
            component: "./My/Service/Service",
            routes: [
              {
                path: "/my/service",
                component: "./My/Service/Service"
              }
            ]
          }
        ]
      },
      //

      //menu
      {
        path: "/menus",
        icon: "menu-fold",
        name: "menus",
        routes: [
          {
            path: "/menus/first",
            name: "first",
            component: "./Menus/First"
          }
        ]
      },
      {
        name: "exception",
        icon: "warning",
        path: "/exception",
        routes: [
          // exception
          {
            path: "/exception/403",
            name: "not-permission",
            component: "./Exception/403"
          },
          {
            path: "/exception/404",
            name: "not-find",
            component: "./Exception/404"
          },
          {
            path: "/exception/500",
            name: "server-error",
            component: "./Exception/500"
          },
          {
            path: "/exception/trigger",
            name: "trigger",
            hideInMenu: true,
            component: "./Exception/TriggerException"
          }
        ]
      },
      {
        component: "404"
      }
    ]
  }
];
