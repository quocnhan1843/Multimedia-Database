/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensionaldata.tree;

import UI.Dictionary;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;
import multidimensionaldata.tree.process.Process;

/**
 *
 * @author ASUS
 */
public class PointQuadTree extends Tree{

    private PointQuadNode root;
    
    @Override
    public PointQuadNode getRoot() {
        return root;
    }

    @Override
    public String getName() {
        return Dictionary.Words.NAME_POINTQUADTREE.getString();
    }

    @Override
    public void setEmpty() {
        this.root = null;
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }
    
    private int positionChild(Point point1, Point point2){
//        Point point1 = current.getPoint();
//        Point point2 = node.getPoint();
        
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

    private void setRoot(PointQuadNode nodePointQuad) {
        this.root = nodePointQuad;
    }
    
    private boolean checkLabel(PointQuadNode current, Node node) {
        if(current == null) return false;
        if(current.getLabel() == node.getLabel()) return true;
        return checkLabel(current.getNodeNW(), node) || 
                    checkLabel(current.getNodeNE(), node) ||
                        checkLabel(current.getNodeSE(), node) ||
                            checkLabel(current.getNodeSW(), node);
    }

    private boolean checkPoint(PointQuadNode current, Point point) {
        if(current == null) return false;
        if(current.getPoint().equalPoint(point)) return true;
        int priority = positionChild(current.getPoint(), point);
        
        if(priority == 1) return checkPoint(current.getNodeNW(), point);
        if(priority == 2) return checkPoint(current.getNodeNE(), point);
        if(priority == 3) return checkPoint(current.getNodeSE(), point);
        return checkPoint(current.getNodeSW(), point);
    }

    @Override
    public void insertNode(String label, Point points, boolean isPaint) {
        super.setSizeUp();
        PointQuadNode pointQuadNode = new PointQuadNode(label, points);
        if(this.root == null){
            this.root = pointQuadNode;
            this.root.setPos(12*3000 + 500, 50);
        }else{
            addNode(this.root, pointQuadNode, isPaint);
        }
    }
    
    private void addNode(PointQuadNode current
            , PointQuadNode pointQuadNode, boolean isPaint){
        if(current == null) return;
        int priority = positionChild(current.getPoint(), pointQuadNode.getPoint());
        boolean ok;
        if(priority == 1){
            ok = true;
            if(current.getNodeNW() == null){
                addChildToNode(current, pointQuadNode, priority);
                updateLocation(root);
            }else{
                ok = false;
            }
            
            if(isPaint){
                runAnimation(current.getxPos(), current.getyPos()
                        , current.getNodeNW().getxPos(), current.getNodeNW().getyPos()
                        , false, "chua co");
            }
            if(!ok){
                 addNode(current.getNodeNW(), pointQuadNode, isPaint);
            }
            
        }else if(priority == 2){
            ok = true;
            if(current.getNodeNE()== null){
                addChildToNode(current, pointQuadNode, priority);
                updateLocation(root);
            }else{
                ok = false;
            }
            if(isPaint){
                runAnimation(current.getxPos(), current.getyPos()
                        , current.getNodeNE().getxPos(), current.getNodeNE().getyPos()
                        , false, "chua co");
            }
            if(!ok){
                addNode(current.getNodeNE(), pointQuadNode, isPaint);
            }
        }else if(priority == 3){
            ok = true;
            if(current.getNodeSE()== null){
                addChildToNode(current, pointQuadNode, priority);
                updateLocation(root);
            }else{
                ok = false;
            }
            if(isPaint){
                runAnimation(current.getxPos(), current.getyPos()
                        , current.getNodeSE().getxPos(), current.getNodeSE().getyPos()
                        , false, "chua co");
            }
            if(!ok){
                addNode(current.getNodeSE(), pointQuadNode, isPaint);
            }
        }else{
            ok = true;
            if(current.getNodeSW()== null){
                addChildToNode(current, pointQuadNode, priority);
                updateLocation(root);
            }else{
                ok = false;
            }
            if(isPaint){
                runAnimation(current.getxPos(), current.getyPos()
                        , current.getNodeSW().getxPos(), current.getNodeSW().getyPos()
                        , false, "chua co");
            }
            if(!ok){
                addNode(current.getNodeSW(), pointQuadNode, isPaint);
            }
        }
    }
    private int countChild(PointQuadNode node){
        if(node == null) return 0;
        return 1 + countChild(node.getNodeNW()) + countChild(node.getNodeNE())
                + countChild(node.getNodeSE()) + countChild(node.getNodeSW());
    }
    private void updateLocation(PointQuadNode node){
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
    
    private void addChildToNode(PointQuadNode pointQuadNodeParent
            , PointQuadNode pointQuadNodeChild, int priority){
        if(priority == 1){
            pointQuadNodeParent.setNodeNW(pointQuadNodeChild);
        }else if(priority == 2){
            pointQuadNodeParent.setNodeNE(pointQuadNodeChild);
        }else if(priority == 3){
            pointQuadNodeParent.setNodeSE(pointQuadNodeChild);
        }else{
            pointQuadNodeParent.setNodeSW(pointQuadNodeChild);
        }
        pointQuadNodeChild.setParent(pointQuadNodeParent);
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
        PointQuadNode pointQuadNode = searchLabel(this.root, label,true, paint);
        deleteNode(pointQuadNode, paint);
    }

    @Override
    public void deleteNodePoint(Point point, boolean paint) {
        PointQuadNode pointQuadNode = searchPoint(this.root, point,true, paint);
        deleteNode(pointQuadNode, paint);
    }
    
    private void deleteNode(PointQuadNode pointQuadNode, boolean  isPaint){
        Queue queueList = new LinkedList();
        if(isPaint == false){
            queueList = getList(pointQuadNode);
        }else{
            Process.addPointDelete(new Point2D(0, 0));
            Process.setNodeDelete(new InfoNode(pointQuadNode.getLabel()
                    , pointQuadNode.getPoint()));
        }
        
        if(pointQuadNode == root) setEmpty();
        else{
            deleteChild(pointQuadNode.getParent(), pointQuadNode);
        }
        
        if(isPaint == false)
            while(!queueList.isEmpty()){
                InfoNode infoNode = (InfoNode) queueList.poll();
                
                Process.addQueueInsert(infoNode);
            }
    }
    
    private void deleteChild(PointQuadNode parent, PointQuadNode child){
        try{
            if(parent.getNodeNW() != null && parent.getNodeNW().equals(child)){
                parent.setNodeNW(null);
                return;
            }
            if(parent.getNodeNE() != null && parent.getNodeNE().equals(child)){
                parent.setNodeNE(null);
                return;
            }
            if(parent.getNodeSE() != null && parent.getNodeSE().equals(child)){
                parent.setNodeSE(null);
                return;
            }
            if(parent.getNodeSW() != null && parent.getNodeSW().equals(child)){
                parent.setNodeSW(null);
                return;
            }
        }catch(NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }
    }
    
    private Queue getList(PointQuadNode current){
        Queue queue =  new LinkedList();
        Stack stack = new Stack();
        if(current == null) return queue;
        stack.add(current);
        while(!stack.empty()){
                PointQuadNode tmp = (PointQuadNode) stack.pop();
                queue.add(new InfoNode(tmp.getLabel(), tmp.getPoint()));

                if(tmp.getNodeNW()!= null ) stack.push(tmp.getNodeNW());
                if(tmp.getNodeNE() != null ) stack.push(tmp.getNodeNE());
                if(tmp.getNodeSE() != null ) stack.push(tmp.getNodeSE());
                if(tmp.getNodeSW() != null ) stack.push(tmp.getNodeSW());
        }
        queue.remove();
        return queue;
    }

    @Override
    public void searchLabelAndPaint(String label, boolean paint) {
        try{
            searchLabel(root, label,false, paint).setColor(Dictionary.COLOR.BACKGROUND_NODE_WHEN_CHOOSE.getColor());
        }catch(NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }
    }
    
    private PointQuadNode searchLabel(PointQuadNode current, String stringLabel,boolean andDelete, boolean isPaint){
        if(current == null) return null;
        if(current.getLabel().equals(stringLabel)){
            if(isPaint){
                Process.setNodeSearch(new InfoNode(current.getLabel(), current.getPoint()));
            }
            return current;
        }
        
        if(isPaint && current.getNodeNW() != null){
            runAnimationSearch(current.getxPos(), current.getyPos()
                    , current.getNodeNW().getxPos(), current.getNodeNW().getyPos()
                    , isPaint, "", andDelete);
        }
        PointQuadNode nodeNW = searchLabel(current.getNodeNW(), stringLabel, andDelete, isPaint);
        if(nodeNW != null)  return nodeNW;
        if(isPaint && current.getNodeNE() != null){
            runAnimationSearch(current.getxPos(), current.getyPos()
                    , current.getNodeNE().getxPos(), current.getNodeNE().getyPos()
                    , isPaint, "", andDelete);
        }
        PointQuadNode nodeNE = searchLabel(current.getNodeNE(), stringLabel, andDelete, isPaint);
        if(nodeNE != null) return nodeNE;
        if(isPaint && current.getNodeSE() != null){
            runAnimationSearch(current.getxPos(), current.getyPos()
                    , current.getNodeSE().getxPos(), current.getNodeSE().getyPos()
                    , isPaint, "", andDelete);
        }
        PointQuadNode nodeSE = searchLabel(current.getNodeSE(), stringLabel, andDelete, isPaint);
        if(nodeSE != null) return nodeSE;
        if(isPaint && current.getNodeSW() != null){
            runAnimationSearch(current.getxPos(), current.getyPos()
                    , current.getNodeSW().getxPos(), current.getNodeSW().getyPos()
                    , isPaint, "", andDelete);
        }
        PointQuadNode nodeSW = searchLabel(current.getNodeSW(), stringLabel, andDelete, isPaint);
        if(nodeSW != null) return nodeSW;
        
        return null;
    }
    private void runAnimationSearch(int xs, int ys, int xf, int yf, boolean isLeave, String string, boolean andDelete){
        int u1 = (xf - xs);
        int u2 = (yf - ys);
        
        for(double t = 0.0; t <= 1.0; t+= 0.001){
            int x = (int) (xs + t*u1);
            int y = (int) (ys + t*u2);
            Process.addPointSearch(new Point2D(x, y)); 
        }
        
        if(!isLeave)
        for(int i=0; i<100; i++){
            Process.addPointSearch(new Point2D(xf, yf));
        }
    }

    @Override
    public void searchPointAndPaint(Point point, boolean paint) {
        try{
            searchPoint(root, point,false, paint).setColor(Dictionary.COLOR.BACKGROUND_NODE_WHEN_CHOOSE.getColor());
        }catch(NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }
    }
    
    private PointQuadNode searchPoint(PointQuadNode current, Point point,boolean andDelete, boolean isPaint){
        if(current == null) return null;
        if(current.getPoint().equalPoint(point)){
            if(isPaint){
                Process.setNodeSearch(new InfoNode(current.getLabel(), current.getPoint()));
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

    private void paint(Graphics2D g, PointQuadNode node) {
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

    @Override
    public boolean checkLabel(String str) {
        return checkLabel(this.root, str);
    }
    
    private boolean checkLabel(PointQuadNode currentPointQuadNode, String stringLabel){
        if(currentPointQuadNode == null) return false;
        if(currentPointQuadNode.getLabel().equals(stringLabel)) return true;
        
        return (checkLabel(currentPointQuadNode.getNodeNW(), stringLabel)
                || checkLabel(currentPointQuadNode.getNodeNE(), stringLabel)
                || checkLabel(currentPointQuadNode.getNodeSE(), stringLabel)
                || checkLabel(currentPointQuadNode.getNodeSW(), stringLabel));
    }

    @Override
    public boolean checkPoint(Point point) {        
        return checkPoint(root, point);
    }

    private PointQuadNode findPointQuadNode(PointQuadNode current, MouseEvent ev){
        if(current == null) return null;
        if(current.contains(ev)){
                return current;
        }
        PointQuadNode n1 = findPointQuadNode(current.getNodeNW(), ev);
        PointQuadNode n2 = findPointQuadNode(current.getNodeNE(), ev);
        PointQuadNode n3 = findPointQuadNode(current.getNodeSE(), ev);
        PointQuadNode n4 = findPointQuadNode(current.getNodeSW(), ev);
        if(n1 != null) return n1;
        if(n2 != null) return n2;
        if(n3 != null) return n3;
        if(n4 != null) return n4;
        return null;
    }
    @Override
    public Node findNode(MouseEvent e) {
        PointQuadNode ans = findPointQuadNode(this.getRoot(), e);
        if(ans == null) return null;
        return ans;
    }

    @Override
    public void resetColor() {
        resetColor(root);
    }
    private void resetColor(PointQuadNode node){
        if(node == null) return;
        node.setColor(Dictionary.COLOR.BACKGROUND_NODE.getColor());

        resetColor(node.getNodeNW());
        resetColor(node.getNodeNE());
        resetColor(node.getNodeSE());
        resetColor(node.getNodeSW());
    }

    @Override
    public void setColor(Node node) {
        
    }

    private void paintNode(Graphics2D g, PointQuadNode node) {
    	node.paint(g);
    }
}
