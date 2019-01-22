function addDept(req, res) {
 // console.log(req.params);
  const { dept } = req.body;
    console.log(dept);
  if (dept.deptName===undefined) {
    res.send({ status: 400, msg: "提交信息为空" });
  } else {
    res.send({ status: 200 });

  }
}

function deleteDept(req, res) {
  //console.log(req.params);
  res.send({
    status: 204,
    msg: "删除成功"
  });
}

export default {
  'POST /api/depts': addDept,
 /* 'POST /depts': addDept,*/
   'DELETE /api/depts/:id': deleteDept,
 /* 'DELETE /depts/:id': deleteDept*/
};
