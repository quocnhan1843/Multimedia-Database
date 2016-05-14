/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensionaldata.tree;

import UI.Dictionary;
import UI.Dictionary.COLOR;
import UI.Dictionary.Words;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

/**
 *
 * @author ASUS
 */
public class MXQuadTree extends Tree{
    private int k;
    private int size;
    private MXQuadNode root;
    private int xMin, xMax, yMin, yMax;    
    
    private MXQuadNode nodeNW;
    private MXQuadNode nodeNE;
    private MXQuadNode nodeSE;
    private MXQuadNode nodeSW;
    private MXQuadNode parent;

    public MXQuadTree() {
        super();
        
        k = 1;
        size = 0;
        root = null;
        
        nodeNW = null;
        nodeNE = null;
        nodeSE = null;
        nodeSW = null;
        parent = null;
        
        xMin = 100000000;
        xMax = 0;
        yMin = 100000000;
        xMax = 0;
    }

    public MXQuadTree(int xMin, int xMax, int yMin, int yMax, MXQuadNode root) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.root = root;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getxMin() {
        return xMin;
    }

    public void setxMin(int xMin) {
        this.xMin = xMin;
    }

    public int getxMax() {
        return xMax;
    }

    public void setxMax(int xMax) {
        this.xMax = xMax;
    }

    public int getyMin() {
        return yMin;
    }

    public void setyMin(int yMin) {
        this.yMin = yMin;
    }

    public int getyMax() {
        return yMax;
    }

    public void setyMax(int yMax) {
        this.yMax = yMax;
    }

    public MXQuadNode getRoot() {
        return root;
    }

    public void setRoot(MXQuadNode root) {
        this.root = root;
    }

    @Override
    public String getName() {
        return Dictionary.Words.NAME_MATRIXQUADTREE.getString();
    }

    @Override
    public void setEmpty() {
        this.root = null;
        super.setSize(0);
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    private boolean checkLabel(MXQuadNode current, Node node) {
        if(current == null) return false;
        if(current.getLabel() == node.getLabel()) return true;
        return checkLabel(current.getNodeNW(), node) ||
                checkLabel(current.getNodeNE(), node) ||
                checkLabel(current.getNodeSE(), node) ||
                checkLabel(current.getNodeSW(), node);
    }

    private boolean checkPoint(MXQuadNode current, Node node) {
        if(current == null) return false;
        if(node.equals(current)) return true;
        
        int priority;
        return false;
    }

    public MXQuadNode getNodeNW() {
        return nodeNW;
    }

    public void setNodeNW(MXQuadNode nodeNW) {
        this.nodeNW = nodeNW;
    }

    public MXQuadNode getNodeNE() {
        return nodeNE;
    }

    public void setNodeNE(MXQuadNode nodeNE) {
        this.nodeNE = nodeNE;
    }

    public MXQuadNode getNodeSE() {
        return nodeSE;
    }

    public void setNodeSE(MXQuadNode nodeSE) {
        this.nodeSE = nodeSE;
    }

    public MXQuadNode getNodeSW() {
        return nodeSW;
    }

    public void setNodeSW(MXQuadNode nodeSW) {
        this.nodeSW = nodeSW;
    }

    public MXQuadNode getParent() {
        return parent;
    }

    public void setParent(MXQuadNode parent) {
        this.parent = parent;
    }
    
    public boolean isLeaves() {
        return this.nodeNW == null && this.nodeNE == null 
                && this.nodeSE == null && this.nodeSW == null;
    }
    
    private int positionChild(Point point1, Point point2){
        
        Vector<Integer> v1 = point1.getLocation();
        Vector<Integer> v2 = point2.getLocation();
        
        int xVal = v1.elementAt(0).intValue();
        int yVal = v1.elementAt(1).intValue();
        
        int x = v2.elementAt(0).intValue();
        int y = v2.elementAt(1).intValue();
        
        if(x < xVal && y >= yVal) return 1;
        if(x >= xVal && y >= yVal) return 2;
        if(x >= xVal && y < yVal) return 3;
        return 4;
    }

    @Override
    public void insertNode(String label, Point points, boolean isPaint) {
        super.setSizeUp();
        MXQuadNode matrixQuadNode = new MXQuadNode(label, points);
        add(matrixQuadNode, isPaint);
    }
    
    public void add(MXQuadNode matrixQuadNode, boolean isPaint){
        if(this.checkLabel(matrixQuadNode.getLabel())) return;
        MXQuadNode node = new MXQuadNode();
        
        int x = matrixQuadNode.getxVal(), y = matrixQuadNode.getyVal();

        if(root == null){
            xMin = x;
            yMin = y;
            xMax = x;
            yMax = y;
            k = 1;
            addNode(matrixQuadNode, isPaint);
            return;
        }
        
        
        if(isChangeSize(x,y)){
            while(xMin + power(2,k) <= xMax || yMin + power(2,k) <= yMax) k++;
            Queue queue = this.getListNode();
            this.root = null;
            while(queue.isEmpty() == false){
                MXQuadNode tmp = (MXQuadNode) queue.poll();
                this.addNode(new MXQuadNode(tmp.getLabel()
                        , new Point(tmp.getxVal(),tmp.getyVal())), isPaint );
            }
        }

        
        addNode(matrixQuadNode, isPaint);
    }
    
    private boolean isChangeSize(int x, int y){
        int old_xMin = xMin;
        int old_xMax = xMax;
        int old_yMin = yMin;
        int old_yMax = yMax;

        xMin = Math.min(xMin, x);
        xMax = Math.max(xMax, x);
        yMin = Math.min(yMin, y);
        yMax = Math.max(yMax, y);
        
        return (old_xMin != xMin || old_xMax != xMax 
                || old_yMin != yMin || old_yMax != yMax);
           
    }
    
    private void addNode(MXQuadNode matrixQuadNode, boolean isPaint){
        int i = xMin, j = yMin, a = power(2,k);
        int x = matrixQuadNode.getxVal(), y = matrixQuadNode.getyVal();
        if(this.root == null){
            root = new MXQuadNode(Words.EMPTY_NODE.getString(), new Point( i + a/2, j + a/2) );
            root.setParent(root);
            root.setPos(12*3000+500, 50);
        }		
        MXQuadNode current = this.root;
        MXQuadNode parent = this.root;
        while(true){
            int posNode = pos(x, y, i + a/2, j + a/2);
            if(a == 1){
                    current.coppyNode(matrixQuadNode);
                    current.setParent(parent);
                    updateLocation(this.root);
                    return;
            }else{
                    parent = current;

                    if(posNode == 1){
                            if(current.getNodeNW() != null){
                                    current = current.getNodeNW();
                                    a /= 2;
                                    j += a;
                                    continue;
                            }
                            current = new MXQuadNode(Words.EMPTY_NODE.getString(), new Point(i + a/2,j + a/2) );
                            parent.setNodeNW(current);
                            current.setParent(parent);
                            j += a/2;
                    }else if(posNode == 2){
                            if(current.getNodeNE() != null){
                                    current = current.getNodeNE();
                                    a /= 2;
                                    i += a;
                                    j += a;
                                    continue;
                            }
                            current = new MXQuadNode(Words.EMPTY_NODE.getString(), new Point(i + a/2,j + a/2) );
                            parent.setNodeNE(current);
                            current.setParent(parent);
                            i += a/2;
                            j += a/2;
                    }else if(posNode == 3){
                            if(current.getNodeSE() != null){
                                    current = current.getNodeSE();
                                    a /= 2;
                                    i += a;
                                    continue;
                            }
                            current = new MXQuadNode(Words.EMPTY_NODE.getString(), new Point(i + a/2,j + a/2));
                            parent.setNodeSE(current);
                            current.setParent(parent);
                            i += a/2;
                    }else{
                            if(current.getNodeSW() != null){
                                    current = current.getNodeSW();
                                    a /= 2;
                                    continue;
                            }
                            current = new MXQuadNode(Words.EMPTY_NODE.getString(),new Point(i + a/2,j + a/2) );
                            parent.setNodeSW(current);
                            current.setParent(parent);
                    }
            }
            a /= 2;
        }
    }
    
    public int pos(int x, int y, int xVal, int yVal){
        if(x < xVal && y >= yVal) return 1;
        if(x >= xVal && y >= yVal) return 2;
        if(x >= xVal && y < yVal) return 3;
        return 4;
    }
    
    public Queue<MXQuadNode> getListNode(){
        Queue<MXQuadNode> queueAns =  new LinkedList();
        Queue<MXQuadNode> queue = new LinkedList();
        if(this.getRoot() == null) return queueAns;
        queue.add(this.getRoot());
        while(!queue.isEmpty()){
            MXQuadNode tmp = (MXQuadNode) queue.poll();
            if(!tmp.getLabel().equals(Words.EMPTY_NODE.getString())) queueAns.add(tmp);

            if(tmp.getNodeNW() != null ) queue.add(tmp.getNodeNW());
            if(tmp.getNodeNE() != null ) queue.add(tmp.getNodeNE());
            if(tmp.getNodeSE() != null ) queue.add(tmp.getNodeSE());
            if(tmp.getNodeSW() != null ) queue.add(tmp.getNodeSW());
        }
        return queueAns;
    }
    
    public int power(int base, int ex){
        if(ex == 0) return 1;
        int ans = power(base, ex/2);
        ans *= ans;
        if(ex % 2 != 0) ans *= base;
        return ans;
    }

    @Override
    public void deleteNodeLabel(String label, boolean paint) {
        super.setSizeDown();
        MXQuadNode mXQuadNode = searchLabel(this.root, label, true, paint);
        deleteNode(mXQuadNode, paint);
    }

    @Override
    public void deleteNodePoint(Point point, boolean paint) {
        super.setSizeDown();
        MXQuadNode mXQuadNode = searchPoint(this.root, point, true, paint);
        deleteNode(mXQuadNode, paint);
    }
    
    private void deleteNode(MXQuadNode mXQuadNode, boolean isPaint){
        if(mXQuadNode == null) return;
        MXQuadNode mX = mXQuadNode.getParent();
        
        if(mX == mXQuadNode) return;
        
        int p = positionChild(mXQuadNode.getPoint(), mX.getPoint());
        
        if(p == 1){
            mX.setNodeNW(null);
        }else if(p == 2){
            mX.setNodeNE(null);
        }else if(p == 3){
             mX.setNodeSE(null);
        }else{
             mX.setNodeSW(null);
        }
        deleteNode(mX, isPaint);
    }
    
    private MXQuadNode searchLabel(MXQuadNode current, String stringLabel
            ,boolean andDelete, boolean isPaint){
        if(current == null) return null;
        if(current.getLabel().equals(stringLabel)){
            if(isPaint){
                multidimensionaldata.tree.process.Process
                        .setNodeSearch(new InfoNode(current.getLabel()
                                , current.getPoint()));
            }
            return current;
        }
        
        if(isPaint && current.getNodeNW() != null){
            runAnimationSearch(current.getxPos(), current.getyPos()
                    , current.getNodeNW().getxPos(), current.getNodeNW().getyPos()
                    , isPaint, "", andDelete);
        }
        MXQuadNode nodeNW = searchLabel(current.getNodeNW(), stringLabel, andDelete, isPaint);
        if(nodeNW != null)  return nodeNW;
        if(isPaint && current.getNodeNE() != null){
            runAnimationSearch(current.getxPos(), current.getyPos()
                    , current.getNodeNE().getxPos(), current.getNodeNE().getyPos()
                    , isPaint, "", andDelete);
        }
        MXQuadNode nodeNE = searchLabel(current.getNodeNE(), stringLabel, andDelete, isPaint);
        if(nodeNE != null) return nodeNE;
        if(isPaint && current.getNodeSE() != null){
            runAnimationSearch(current.getxPos(), current.getyPos()
                    , current.getNodeSE().getxPos(), current.getNodeSE().getyPos()
                    , isPaint, "", andDelete);
        }
        MXQuadNode nodeSE = searchLabel(current.getNodeSE(), stringLabel, andDelete, isPaint);
        if(nodeSE != null) return nodeSE;
        if(isPaint && current.getNodeSW() != null){
            runAnimationSearch(current.getxPos(), current.getyPos()
                    , current.getNodeSW().getxPos(), current.getNodeSW().getyPos()
                    , isPaint, "", andDelete);
        }
        MXQuadNode nodeSW = searchLabel(current.getNodeSW(), stringLabel, andDelete, isPaint);
        if(nodeSW != null) return nodeSW;
        
        return null;
    }
    
    private MXQuadNode searchPoint(MXQuadNode current, Point point,boolean andDelete, boolean isPaint){
        if(current == null) return null;
        if(current.getPoint().equalPoint(point)){
            if(isPaint){
                multidimensionaldata.tree.process.Process.setNodeSearch(new InfoNode(current.getLabel(), current.getPoint()));
            }
            return current;
        }
        
        int priority = positionChild(current.getPoint(), point);
        
        if(priority == 1){
            if(isPaint && current.getNodeNW() != null){
                runAnimationSearch(current.getxPos(), current.getyPos()
                        , current.getNodeNW().getxPos(), current.getNodeNW().getyPos()
                        , isPaint, "", andDelete);
            }
            return searchPoint(current.getNodeNW(), point, andDelete, isPaint);
        }
        if(priority == 2){
            if(isPaint && current.getNodeNE() != null){
                runAnimationSearch(current.getxPos(), current.getyPos()
                        , current.getNodeNE().getxPos(), current.getNodeNE().getyPos()
                        , isPaint, "", andDelete);
            }
            return searchPoint(current.getNodeNE(), point, andDelete, isPaint);
        }
        if(priority == 3){
            if(isPaint && current.getNodeSE() != null){
                runAnimationSearch(current.getxPos(), current.getyPos()
                        , current.getNodeSE().getxPos(), current.getNodeSE().getyPos()
                        , isPaint, "", andDelete);
            }
            return searchPoint(current.getNodeSE(), point, andDelete, isPaint);
        }
        
        if(isPaint && current.getNodeSW() != null){
                runAnimationSearch(current.getxPos(), current.getyPos()
                        , current.getNodeSW().getxPos(), current.getNodeSW().getyPos()
                        , isPaint, "", andDelete);
            }
        return searchPoint(current.getNodeSW(), point, andDelete, isPaint);        
    }
    
    private void runAnimationSearch(int xs, int ys, int xf, int yf
            , boolean isLeave, String string, boolean andDelete){
        int u1 = (xf - xs);
        int u2 = (yf - ys);
        
        for(double t = 0.0; t <= 1.0; t+= 0.001){
            int x = (int) (xs + t*u1);
            int y = (int) (ys + t*u2);
            multidimensionaldata.tree.process.Process.addPointSearch(new Point2D(x, y)); 
        }
        
        if(!isLeave)
        for(int i=0; i<100; i++){
            multidimensionaldata.tree.process.Process.addPointSearch(new Point2D(xf, yf));
        }
    }

    @Override
    public void searchLabelAndPaint(String label, boolean paint) {
        try{
            searchLabel(this.root, label, false, paint)
                    .setColor(COLOR.BACKGROUND_NODE_WHEN_CHOOSE.getColor());
        }catch(NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }
    }

    @Override
    public void searchPointAndPaint(Point point, boolean paint) {
        try{
            searchPoint(this.root, point, false, paint)
                    .setColor(COLOR.BACKGROUND_NODE_WHEN_CHOOSE.getColor());
        }catch(NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }
    }

    @Override
    public void paintTree() {
        Graphics2D g2 = (Graphics2D) super.getGraphics2D();
		
        if(this.getRoot()!= null){
        	paint(g2, this.root);
        }else{
        	g2.setFont(new Font(Dictionary.Font.DEFAULT.getString()
                        , Font.BOLD, Dictionary.Font_Size.TREE_TEXT.getValue()) );
        	g2.drawString(Dictionary.Words.EMPTY_TREE.getString(),12 * 3000 + 500, 50);
        }
    }
    private void paint(Graphics2D g, MXQuadNode node) {
        if(node == null) return;

        paintNode(g, node);

        g.setColor(Color.black);

        int height = Dictionary.SIZE.HEIGHT.getValue();
        int width  = Dictionary.SIZE.WIDTH.getValue();
        
        int dx = width/8;
        
        if(node.getNodeNW() != null){
                g.drawLine(node.getxPos() + dx, node.getyPos() + height
                        , node.getNodeNW().getxPos() + width/2, node.getNodeNW().getyPos());
                paint(g, node.getNodeNW());
        }
        if(node.getNodeNE()!= null){	
                g.drawLine(node.getxPos() + 3*dx, node.getyPos() + height
                        , node.getNodeNE().getxPos() + width/2, node.getNodeNE().getyPos());
                paint(g, node.getNodeNE());
        }
        if(node.getNodeSE()!= null){
                g.drawLine(node.getxPos() + 5*dx, node.getyPos() + height
                        , node.getNodeSE().getxPos() + width/2, node.getNodeSE().getyPos());
                paint(g, node.getNodeSE());
        }
        if(node.getNodeSW()!= null){
                g.drawLine(node.getxPos() + 7*dx, node.getyPos() + height
                        , node.getNodeSW().getxPos() + width/2, node.getNodeSW().getyPos());
                paint(g, node.getNodeSW());
        }
    }
    
    private void paintNode(Graphics2D g2, MXQuadNode node) {
        node.paint(g2);
    }

    @Override
    public boolean checkLabel(String str) {
        return checkLabel(this.root, str);
    }
    private boolean checkLabel(MXQuadNode current, String stringLabel) {
        if(current == null) return false;
        if(current.getLabel().equals(stringLabel)) return true;
        
        return (checkLabel(current.getNodeNW(), stringLabel)
                || checkLabel(current.getNodeNE(), stringLabel)
                || checkLabel(current.getNodeSE(), stringLabel)
                || checkLabel(current.getNodeSW(), stringLabel));
    }

    @Override
    public boolean checkPoint(Point point) {
        return checkPoint(root, point);
    }

    @Override
    public Node findNode(MouseEvent e) {
        MXQuadNode ans = findNode(this.getRoot(), e);
        if(ans == null) return null;
        return ans;
    }
    
    private MXQuadNode findNode(MXQuadNode current, MouseEvent ev) {
        if(current == null) return null;
        if(current.contains(ev)){
                return current;
        }
        MXQuadNode n1 = findNode(current.getNodeNW(), ev);
        MXQuadNode n2 = findNode(current.getNodeNE(), ev);
        MXQuadNode n3 = findNode(current.getNodeSE(), ev);
        MXQuadNode n4 = findNode(current.getNodeSW(), ev);
        if(n1 != null) return n1;
        if(n2 != null) return n2;
        if(n3 != null) return n3;
        if(n4 != null) return n4;
        return null;
    }

    @Override
    public void resetColor() {
        
    }

    @Override
    public void setColor(Node node) {
        
    }

    private boolean checkPoint(MXQuadNode current, Point point) {
        if(current == null) return false;
        if(current.getPoint().equalPoint(point) 
                && !current.getLabel().equals(Words.EMPTY_NODE.getString())) return true;
        
        int priority = positionChild(current.getPoint(), point);
        
        if(priority == 1) return checkPoint(current.getNodeNW(), point);
        if(priority == 2) return checkPoint(current.getNodeNE(), point);
        if(priority == 3) return checkPoint(current.getNodeSE(), point);
        return checkPoint(current.getNodeSW(), point);
    }   

    private void updateLocation(MXQuadNode node) {
        int x = node.getxPos();
        int y = node.getyPos();

        int cnt1, cnt2, cnt3, cnt4;

        cnt1 = countChild(node.getNodeNW());
        cnt2 = countChild(node.getNodeNE());
        cnt3 = countChild(node.getNodeSE());
        cnt4 = countChild(node.getNodeSW());

        int dx12 = 60*(cnt3 + cnt4);
        int dx34 = 60*(cnt1 + cnt2);

        if(node == root){
                dx12 += 120;
                dx34 += 120;
        }

        int x12 = x - dx12 - 60;
        int x34 = x + dx34 + 60;

        int dx1 = 60*cnt2;
        int dx2 = 60*cnt1;
        int dx3 = 60*cnt4;
        int dx4 = 60*cnt3;

        int x1 = x12 - dx1 - 60;
        int x2 = x12 + dx2 + 60;
        int x3 = x34 - dx3 - 60;
        int x4 = x34 + dx4 + 60;

        if(node.getNodeNW() != null){
                node.getNodeNW().setPos(x1, y + 100);
                updateLocation(node.getNodeNW());
        }
        if(node.getNodeNE() != null){	
                node.getNodeNE().setPos(x2, y + 100);
                updateLocation(node.getNodeNE());
        }
        if(node.getNodeSE() != null){
                node.getNodeSE().setPos(x3, y + 100);
                updateLocation(node.getNodeSE());
        }
        if(node.getNodeSW() != null){
                node.getNodeSW().setPos(x4, y + 100);
                updateLocation(node.getNodeSW());
        }
    }
    private int countChild(MXQuadNode node){
        if(node == null) return 0;
        return 1 + countChild(node.getNodeNW()) + countChild(node.getNodeNE())
                + countChild(node.getNodeSE()) + countChild(node.getNodeSW());
    }
}
