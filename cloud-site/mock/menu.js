function addMenu(req, res) {
    // console.log(req.params);
    const { menu } = req.body;
    // console.log(dept);
    if (menu.name===undefined) {
        res.send({ status: 400, msg: "提交信息为空" });
    } else {
        res.send({ status: 200 });

    }
}

function deleteMenu(req, res) {
    //console.log(req.params);
    res.send({
        status: 204,
        msg: "删除成功"
    });
}

export default {
    'POST /api/menus': addMenu,
    'DELETE /api/menus': deleteMenu,

};
