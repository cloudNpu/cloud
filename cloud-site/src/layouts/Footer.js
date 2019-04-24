import React, { Fragment } from 'react';
import { Layout, Icon } from 'antd';
import GlobalFooter from '@/components/GlobalFooter';

const { Footer } = Layout;
const FooterView = () => (
  <Footer style={{ padding: 0 }}>
    <GlobalFooter
      links={[
        {
          key: '服务运行管理中心',
          title: '服务运行管理中心',
       //   href: 'https://pro.ant.design',
          blankTarget: true,
        },
        // {
        //   key: 'github',
        //   title: <Icon type="github" />,
        //   href: 'https://github.com/ant-design/ant-design-pro',
        //   blankTarget: true,
        // },
        // {
        //   key: 'Ant Design',
        //   title: 'Ant Design',
        //   href: 'https://ant.design',
        //   blankTarget: true,
        // },
      ]}
      copyright={
        <Fragment>
          Copyright <Icon type="copyright" /> 2019 服务计算实验室出品
        </Fragment>
      }
    />
  </Footer>
);
export default FooterView;
