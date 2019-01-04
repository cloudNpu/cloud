package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.Menu;
import com.kenji.cloud.service.MenuRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @author SHI Jing
 * @date 2018/12/26 10:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuServiceImplTest {

    @Autowired
    private MenuRoleService menuRoleService;

    @Test
    public void test(){
        Map<String, String> map = menuRoleService.findAll();

        System.out.println("----------------------------------------------------");

        System.out.println("----------------------------------------------------");
    }

//    private TreeNode algorithm(List<Menu> menuList) {
//        //建树.this和parent为空
//        TreeNode root = new TreeNode();
//        //TreeNode(Menu thisMenu, Menu parent, List<TreeNode> children
//
//        for (Menu m : menuList){
//            //建立本结点
//            TreeNode mTree = new TreeNode();
//            mTree.setThisMenu(m);
//
//
//            if(m.getMenu() == null){
//                //作为root下的节点
//                root.addChild(mTree);
//            }else{
//                //建立父节点
//                TreeNode mParentTree = new TreeNode();
//                mParentTree.setThisMenu(m.getMenu());
//
//                //查找父亲，并加入到父亲的孩子下面
//                TreeNode parentNode = search(root, mParentTree);
//                parentNode.addChild(mTree);
//            }
//
//        }
//
//        return root;
//    }
//
//    private TreeNode search(TreeNode root, TreeNode mTree) {
//        if (root.getThisMenu() != null){
//            if( root.getThisMenu().getName().equals(mTree.getThisMenu().getName()) )
//                return root;
//        }
//
//
//
//  //      if (root.getChildren().size() != 0){
//             //在孩子里面找
//            for (int i = 0 ; i < root.getChildren().size() ; i++){
//                TreeNode treeNode = root.getChildren().get(i);
//                TreeNode searchNode = search(treeNode,mTree);
//                if (searchNode != null)
//                    return searchNode;
//            }
//   //     }
//
//          return null;
//
//    }


}
