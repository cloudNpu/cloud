import dva from 'dva';
import createLoading from 'dva-loading';

const runtimeDva = window.g_plugins.mergeConfig('dva');
let app = dva({
  history: window.g_history,
  
  ...(runtimeDva.config || {}),
});

window.g_app = app;
app.use(createLoading());
(runtimeDva.plugins || []).forEach(plugin => {
  app.use(plugin);
});

app.model({ namespace: 'global', ...(require('/Users/kenji/IdeaProjects/pm/pm-site/src/models/global.js').default) });
app.model({ namespace: 'list', ...(require('/Users/kenji/IdeaProjects/pm/pm-site/src/models/list.js').default) });
app.model({ namespace: 'login', ...(require('/Users/kenji/IdeaProjects/pm/pm-site/src/models/login.js').default) });
app.model({ namespace: 'project', ...(require('/Users/kenji/IdeaProjects/pm/pm-site/src/models/project.js').default) });
app.model({ namespace: 'setting', ...(require('/Users/kenji/IdeaProjects/pm/pm-site/src/models/setting.js').default) });
app.model({ namespace: 'user', ...(require('/Users/kenji/IdeaProjects/pm/pm-site/src/models/user.js').default) });
app.model({ namespace: 'register', ...(require('/Users/kenji/IdeaProjects/pm/pm-site/src/pages/User/models/register.js').default) });
app.model({ namespace: 'activities', ...(require('/Users/kenji/IdeaProjects/pm/pm-site/src/pages/Dashboard/models/activities.js').default) });
app.model({ namespace: 'chart', ...(require('/Users/kenji/IdeaProjects/pm/pm-site/src/pages/Dashboard/models/chart.js').default) });
app.model({ namespace: 'monitor', ...(require('/Users/kenji/IdeaProjects/pm/pm-site/src/pages/Dashboard/models/monitor.js').default) });
app.model({ namespace: 'form', ...(require('/Users/kenji/IdeaProjects/pm/pm-site/src/pages/Forms/models/form.js').default) });
app.model({ namespace: 'rule', ...(require('/Users/kenji/IdeaProjects/pm/pm-site/src/pages/List/models/rule.js').default) });
app.model({ namespace: 'profile', ...(require('/Users/kenji/IdeaProjects/pm/pm-site/src/pages/Profile/models/profile.js').default) });
app.model({ namespace: 'error', ...(require('/Users/kenji/IdeaProjects/pm/pm-site/src/pages/Exception/models/error.js').default) });
app.model({ namespace: 'geographic', ...(require('/Users/kenji/IdeaProjects/pm/pm-site/src/pages/Account/Settings/models/geographic.js').default) });
