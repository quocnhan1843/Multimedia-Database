/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensionaldata.tree;

import multidimensionaldata.tree.process.Process;
import UI.Dictionary;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import multidimensionaldata.control.ControlTreePanel;

/**
 *
 * @author ASUS
 */
public class KDimensionalTree extends Tree{
    
    private KDimensionalNode root;

    public KDimensionalTree() {
        super();
        this.root = null;
        super.setNumOfDimension(2);
    }
    
    public KDimensionalTree(int k) {
        super();
        this.root = null;
        super.setNumOfDimension(k);
    }
    
    @Override
    public void setNumOfDimension(int num){
        super.setNumOfDimension(num);
        this.root = null;
    }
    
    public KDimensionalNode getRoot() {
        return root;
    }

    public void setRoot(KDimensionalNode root) {
        this.root = root;
    }

    @Override
    public void insertNode(String label, Point point, boolean paint) {
        Node node = new KDimensionalNode(label, point);
        super.setSizeUp();
        if(this.searchNodePreInsert(node)) return;
        if(this.root == null){
            this.setRoot(new KDimensionalNode(label, point));
            this.getRoot().setLevel(0);
            this.getRoot().setPos(12*3000 + 500, 50);
            return;
        }
        goChild(this.root, new KDimensionalNode(node, 0, null, null), paint);
    }
    private void goChild(KDimensionalNode current,  KDimensionalNode tempNode, boolean paint){

        if(tempNode.greaterNode(current, current.getLevel() % super.getNumOfDimension())){
            updateNode(current.getLeftChild(), -60);
            if(current.getRightChild() == null){
                tempNode.setPos(current.getxPos() + 60, current.getyPos() + 100);
                tempNode.setLevel(current.getLevel() + 1);
                
                current.setRightChild(tempNode);
                
                if(paint)
                    runAnimation(current.getxPos(), current.getyPos()
                            , current.getxPos() + 60, current.getyPos() + 100, true, "La con phai");
            }else{
                if(paint)
                    runAnimation(current.getxPos(), current.getyPos()
                            , current.getRightChild().getxPos(), current.getRightChild().getyPos(), false,"Di ve ben phai");
                goChild(current.getRightChild(),tempNode, paint);
            }
        }else{
            updateNode(current.getRightChild(), 60);
            if(current.getLeftChild() == null){
                tempNode.setPos(current.getxPos() - 60, current.getyPos() + 100);
                tempNode.setLevel(current.getLevel() + 1);
                
                current.setLeftChild(tempNode);
                
                if(paint)
                    runAnimation(current.getxPos(), current.getyPos()
                            , current.getxPos() - 60, current.getyPos() + 100, true, "La con trai");
            }else{
                if(paint)
                    runAnimation(current.getxPos(), current.getyPos()
                            , current.getLeftChild().getxPos(), current.getLeftChild().getyPos(), false, "Di ve ben trai");
                goChild(current.getLeftChild(), tempNode, paint);
            }
        }
    }
    
    private void runAnimation(int xs, int ys, int xf, int yf, boolean isLeave, String string){

        int u1 = (xf - xs);
        int u2 = (yf - ys);
        
        for(double t = 0.0; t <= 1.0; t+= 0.001){
            int x = (int) (xs + t*u1);
            int y = (int) (ys + t*u2);
            
            Process.addPointInsert(new Point2D(x, y), string);
        }
        
        if(!isLeave)
        for(int i=0; i<100; i++){
            Process.addPointInsert(new Point2D(xf, yf),string);
        }
    }

    @Override
    public void deleteNodeLabel(String label, boolean paint) {
        try{
            super.setSizeDown();
            KDimensionalNode node  = new KDimensionalNode(label, new Point());
            KDimensionalNode current = searchNodeLabel(this.root, node, paint);
            if(current == null){
                return;
            }
            if(current.isLeaves()){
                Process.setNodeDelete(new InfoNode(label, new Point()), new InfoNode(label, new Point()));
                Process.addPointSwap(new Point2D(0, 0), new Point2D(1, 1));
            }
            removeNode(current, current.getLevel() % super.getNumOfDimension(), paint);
//            if(this.root != null)
//                updatePositionNode(this.root,this.root.getxPos(), this.root.getyPos());
        }catch(NullPointerException nullPointerException){
            
        }
    }
    
    @Override
    public void deleteNodePoint(Point point, boolean paint) {
        try{
            super.setSizeDown();
            KDimensionalNode node  = new KDimensionalNode("", point);
            KDimensionalNode current = searchNodePoint(this.root, node, paint);
            if(current == null){
                return;
            }
            if(current.isLeaves()){
                Process.setNodeDelete(new InfoNode(current.getLabel(), new Point())
                        , new InfoNode(current.getLabel(), new Point()));
                Process.addPointSwap(new Point2D(0, 0), new Point2D(1, 1));
            }
            removeNode(current, current.getLevel() % super.getNumOfDimension(), paint);
//            if(this.root != null)
//                updatePositionNode(this.root, this.root.getxPos(), this.root.getyPos());
        }catch(NullPointerException nullPointerException){
            
        }
    }
    
    private void updatePositionNode(KDimensionalNode kDimensionalNode, int xPos, int yPos){
        if(kDimensionalNode == null) return;
        kDimensionalNode.setPos(xPos, yPos);
        updatePositionNode(kDimensionalNode.getLeftChild(), xPos 
                - (60*countChild(kDimensionalNode.getRightChild()) - 1), yPos + 100);
        updatePositionNode(kDimensionalNode.getRightChild(), xPos
                + (60*countChild(kDimensionalNode.getLeftChild()) - 1), yPos + 100);
    }
    
    private int countChild(KDimensionalNode kDimensionalNode){
        if(kDimensionalNode == null) return 1;
        return 1 + countChild(kDimensionalNode.getLeftChild()) 
                + countChild(kDimensionalNode.getRightChild());
    }

    @Override
    public String getName() {
        return Dictionary.Words.NAME_KDIMENSIONALTREE.getString();
    }

    @Override
    public void setEmpty() {
        this.root = null;
        super.setSize(0);
    }
    
    private boolean searchNodePreInsert(Node node) {
        return searchNodePreInsert(this.getRoot(), node);
    }

    @Override
    public boolean isEmpty() {
        return this.getRoot() == null;
    }

    private void removeNode(KDimensionalNode current, int k, boolean paint) {
        if(current.isLeaves()){
            if(current.equals(root)){
                this.root = null;
                return;
            }
            if(current.isLeftChild(current.getParent())){
                current.getParent().setLeftChild(null);
            }else{
                current.getParent().setRightChild(null);
            }
            return;
        }
        KDimensionalNode minNode = new KDimensionalNode();
        if(current.getRightChild() != null){ // Truong hop co con phai    
            minNode = searchMinNode(current.getRightChild(), k);
            if(paint){
                runAnimationSwap(current.getxPos(), current.getyPos(), minNode.getxPos(), minNode.getyPos());
            }
        }else{                               // Truong hop khong co con phai
            minNode = searchMinNode(current.getLeftChild(), k);
            if(paint){                
                runAnimationSwap(current.getxPos(), current.getyPos(), minNode.getxPos(), minNode.getyPos());
            }
            current.setRightChild(current.getLeftChild());
            current.setLeftChild(null);
        }
        Process.setNodeDelete(new InfoNode(current.getLabel(), current.getPoint())
                                                ,new InfoNode(minNode.getLabel(), minNode.getPoint()));
        
        ControlTreePanel.swapValue(current.getLabel(),minNode.getLabel());
//        Process.treePaint.setColor(current);
//        Process.treePaint.setColor(minNode);
        current.swapNode(minNode);
        removeNode(minNode, k, paint);
    }
    
    public void setColor(Node node){
        searchNode(root, new KDimensionalNode(node.getLabel(), node.getPoint()))
                .setColor(Dictionary.COLOR.BACKGROUND_SWAP.getColor());
    }
    
    private void updatePosAfterSwap(KDimensionalNode current, int distance){
        
        if(current == null) return;
        
        current.setxPos(current.getxPos() + 2*distance);
        updatePosAfterSwap(current.getLeftChild(), distance);
        updatePosAfterSwap(current.getRightChild(), distance);
    }
    
    private void runAnimationSwap(int xs, int ys, int xf, int yf){

        int u1 = (xf - xs);
        int u2 = (yf - ys);
        
        for(double t = 0.0; t <= 1.0; t+= 0.001){
            int x1 = (int) (xs + t*u1);
            int y1 = (int) (ys + t*u2);
            
            int x2 = (int) (xf - t*u1);
            int y2 = (int) (yf - t*u2);
            
            Process.addPointSwap(new Point2D(x1, y1), new Point2D(x2, y2));
        }
        
        Process.addPointSwap(new Point2D(-1000, -1000), new Point2D(-1000, -1000));
    }
    private void runAnimationSearch(int xs, int ys, int xf, int yf, boolean isLeave){

        int u1 = (xf - xs);
        int u2 = (yf - ys);
        
        for(double t = 0.0; t <= 1.0; t+= 0.001){
            int x1 = (int) (xs + t*u1);
            int y1 = (int) (ys + t*u2);
            
            Process.addPointSearch(new Point2D(x1, y1));
        }
        
        if(!isLeave)
        for(int i=0; i<1000; i++){
          //  Process.addNodeInsert(new Point2D(xf, yf));
        }
    }

    private KDimensionalNode searchMinNode(KDimensionalNode current, int k) {
        if(current == null) return null;
        if(current.isLeaves()) return current;
        
        KDimensionalNode nodeLeft = searchMinNode(current.getLeftChild(), k);
        KDimensionalNode nodeRight = searchMinNode(current.getRightChild(), k);
        
        if(nodeLeft != null && nodeRight != null){
            if(current.less(nodeLeft, k) && current.less(nodeRight, k)){
                return current;
            }else{
                if(nodeLeft.less(nodeRight, k)){
                    return nodeLeft;
                }
                return nodeRight;
            }
        }else if(nodeLeft != null){
            if(nodeLeft.greaterNode(current, k)){
                return current;
            }
            return nodeLeft;
        }else if(nodeRight != null){
            if(current.less(nodeRight, k)){
                return current;
            }
            return nodeRight;
        }
        return current;
    }

    private boolean searchNodePreInsert(KDimensionalNode current, Node node) {
        if(current == null) return false;
        if(node.equals(current)) return true;
        if(node.greaterNode(current, current.getLevel() % super.getNumOfDimension())){
            return searchNodePreInsert(current.getRightChild(), node);
        }return searchNodePreInsert(current.getLeftChild(), node);
    }

    private KDimensionalNode searchNode(KDimensionalNode current, KDimensionalNode node) {
        KDimensionalNode node1 = searchNodeLabel(current, node, false);
        if(node1 != null) return node1;
        KDimensionalNode node2 = searchNodePoint(current, node, false);
        if(node2 != null) return node2;
        return null;
    }
    private KDimensionalNode searchNodeLabel(KDimensionalNode current, KDimensionalNode node, boolean paint){
        if(current == null) return null;
        if(current.getLabel().equals(node.getLabel())){
            Process.setNodeSearch(new InfoNode(node.getLabel(), node.getPoint()));
            return current;
        }
        if(paint){
            if(current.getLeftChild() != null)
                runAnimationSearch(current.getxPos(), current.getyPos()
                    , current.getLeftChild().getxPos(), current.getLeftChild().getyPos(), paint);
        }
        KDimensionalNode leftChild = searchNodeLabel(current.getLeftChild(), node, paint);
        if(leftChild != null) return leftChild;
         if(paint){
             if(current.getRightChild()!= null)
                runAnimationSearch(current.getxPos(), current.getyPos()
                                    , current.getRightChild().getxPos(), current.getRightChild().getyPos(), paint);
        }
        KDimensionalNode rightChild = searchNodeLabel(current.getRightChild(), node, paint);
        if(rightChild != null) return rightChild;
        return null;        
    }
    private KDimensionalNode searchNodePoint(KDimensionalNode current, KDimensionalNode node, boolean paint){
        if(current == null) return null;
        if(node.getPoint().equalPoint(current.getPoint())){
            Process.setNodeSearch(new InfoNode(node.getLabel(), node.getPoint()));
            return current;
        }
        if(node.greaterNode(current, current.getLevel()% super.getNumOfDimension())){
            
            if(paint){
                if(current.getRightChild()!= null)
                    runAnimationSearch(current.getxPos(), current.getyPos()
                                    , current.getRightChild().getxPos(), current.getRightChild().getyPos(), paint);
            }
            
            return searchNodePoint(current.getRightChild(), node, paint);
        }
        if(paint){
            if(current.getLeftChild() != null)
                runAnimationSearch(current.getxPos(), current.getyPos()
                    , current.getLeftChild().getxPos(), current.getLeftChild().getyPos(), paint);
        }
        return searchNodePoint(current.getLeftChild(), node, paint);
    }
    
    private boolean checkLabel(KDimensionalNode current, String label) {
        if(current == null) return false;
        if(current.getLabel().equals(label)) return true;
        return checkLabel(current.getLeftChild(), label) ||
                checkLabel(current.getRightChild(), label);
    }

    private boolean checkPoint(KDimensionalNode current, Point point) {
        if(current == null) return false;
        if(current.getPoint().equalPoint(point)) return true;
        if(point.greaterPoint(current.getPoint(), current.getLevel()%super.getNumOfDimension())) return checkPoint(current.getRightChild(), point);
        return checkPoint(current.getLeftChild(), point);
    }

    @Override
    public void paintTree() {
        Graphics2D g2 = super.getGraphics2D();         
        if(!this.isEmpty()){
            paint(g2,this.getRoot());
        }else{
            g2.setColor(Dictionary.COLOR.DEFAULT.getColor());
            g2.setFont(new Font(Dictionary.Words.DEFAULT_FONT.getString()
                    , Font.BOLD,Dictionary.Font_Size.TREE_TEXT.getValue()) );
            g2.drawString(Dictionary.Words.EMPTY_TREE.getString(),12*3000 + 500, 50);
        }
    }

    private void paint(Graphics2D g2, KDimensionalNode node) {
        if(node == null) return;
        paintNode(g2, node);
        paint(g2, node.getLeftChild());
        paint(g2, node.getRightChild());
    }

    private void paintNode(Graphics2D g, KDimensionalNode node) {
        node.paint(g, super.getNumOfDimension());
    }
    
    private void updateNode(KDimensionalNode node, int value){
        if(node == null) return;

        node.setxPos(node.getxPos() + value);		

        updateNode(node.getLeftChild(), value);
        updateNode(node.getRightChild(), value);
    }

    @Override
    public boolean checkLabel(String str) {
        return checkLabel(root, str);
    }

    @Override
    public boolean checkPoint(Point point) {
        return checkPoint(root, point);
    }

    @Override
    public Node findNode(MouseEvent e) {
        KDimensionalNode ans = findNode(this.getRoot(), e);
        if(ans == null) return null;
        return ans;
    }
    private KDimensionalNode findNode(KDimensionalNode current, MouseEvent ev){
        if(current == null) return null;
        if(current.contains(ev)){
                return current;
        }
        KDimensionalNode l = findNode(current.getLeftChild(), ev);
        KDimensionalNode r = findNode(current.getRightChild(), ev);
        if(l != null) return l;
        if(r != null) return r;
        return null;
    }

    @Override
    public void searchLabelAndPaint(String label, boolean paint) {
        try{
            KDimensionalNode node = new KDimensionalNode(label, new Point());
            KDimensionalNode nodeSearch = searchNodeLabel(this.root, node, paint);
            nodeSearch.setColor(Dictionary.COLOR.BACKGROUND_NODE_WHEN_CHOOSE.getColor());
        }catch(NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }
    }

    @Override
    public void searchPointAndPaint(Point point, boolean paint) {
        try{
            KDimensionalNode node = new KDimensionalNode("", point);
            KDimensionalNode nodeSearch = searchNodePoint(this.root, node, paint);
            nodeSearch.setColor(Dictionary.COLOR.BACKGROUND_NODE_WHEN_CHOOSE.getColor());
        }catch(NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }
    }

    @Override
    public void resetColor() {
        resetColor(this.root);
    }
    
    public void resetColor(KDimensionalNode node) {
        if(node == null) return;
        node.setColor(Dictionary.COLOR.BACKGROUND_NODE.getColor());
        resetColor(node.getLeftChild());
        resetColor(node.getRightChild());
    }
}
