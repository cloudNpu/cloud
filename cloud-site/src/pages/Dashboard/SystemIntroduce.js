import React, { memo, PureComponent } from "react";
import { Row, Col, Icon, Tooltip, Table, Modal, Form } from "antd";
import { FormattedMessage } from "umi/locale";
import styles from "./Analysis.less";
import {
  ChartCard,
  MiniArea,
  MiniBar,
  MiniProgress,
  Field
} from "@/components/Charts";
import Trend from "@/components/Trend";
import numeral from "numeral";
import Yuan from "@/utils/Yuan";
import Invoke from "./Invoke";
import PieChart from "./PieChart";

const topColResponsiveProps = {
  xs: 30,
  sm: 10,
  md: 10,
  lg: 10,
  xl: 12,
  style: { marginBottom: 30 }
};

const IntroduceRow = memo(({ loading, visitData }) => (
  <Row gutter={30}>
    <Col {...topColResponsiveProps}>
      <h4>current-memory-usage</h4>
      <PieChart />
    </Col>
    <Col {...topColResponsiveProps}>
      <h4>InvokeCount (high->low)</h4>
      <Invoke />
    </Col>
  </Row>
));

export default IntroduceRow;
