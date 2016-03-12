/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensionaldata.tree;

import UI.Dictionary;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Vector;

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
    
    private int positionChild(Node current, Node node){
        Point point1 = current.getPoint();
        Point point2 = node.getPoint();
        
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

    private boolean checkPoint(PointQuadNode current, Node node) {
        if(current == null) return false;
        if(current.equals(node)) return true;
        int priority = positionChild(current, node);
        
        if(priority == 1) return checkPoint(current.getNodeNW(), node);
        if(priority == 2) return checkPoint(current.getNodeNE(), node);
        if(priority == 3) return checkPoint(current.getNodeSE(), node);
        return checkPoint(current.getNodeSW(), node);
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
        int priority = positionChild(current, pointQuadNode);
        
        if(priority == 1){
            if(current.getNodeNW() == null){
                addChildToNode(current, pointQuadNode, priority);
                updateLocation(root);
                return;
            }
            addNode(current.getNodeNW(), pointQuadNode, isPaint);
        }else if(priority == 2){
            if(current.getNodeNE()== null){
                addChildToNode(current, pointQuadNode, priority);
                updateLocation(root);
                return;
            }
            addNode(current.getNodeNE(), pointQuadNode, isPaint);
        }else if(priority == 3){
            if(current.getNodeSE()== null){
                addChildToNode(current, pointQuadNode, priority);
                updateLocation(root);
                return;
            }
            addNode(current.getNodeSE(), pointQuadNode, isPaint);
        }else{
            if(current.getNodeSW()== null){
                addChildToNode(current, pointQuadNode, priority);
                updateLocation(root);
                return;
            }
            addNode(current.getNodeSW(), pointQuadNode, isPaint);
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
            
            ProcessesPaintTree.addPointInsert(new Point2D(x, y), string);
        }
        
        if(!isLeave)
        for(int i=0; i<100; i++){
            ProcessesPaintTree.addPointInsert(new Point2D(xf, yf),string);
        }
    }

    @Override
    public void deleteNodeLabel(String label, boolean paint) {
    }

    @Override
    public void deleteNodePoint(Point point, boolean paint) {
    }

    @Override
    public void searchLabelAndPaint(String label, boolean paint) {
    }

    @Override
    public void searchPointAndPaint(Point point, boolean paint) {
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

        if(node.getNodeNW() != null){
                g.drawLine(node.getxPos() + 15, node.getyPos() + 28, node.getNodeSW().getxPos() + 60, node.getNodeSW().getyPos());
                paint(g, node.getNodeNW());
        }
        if(node.getNodeNE()!= null){	
                g.drawLine(node.getxPos() + 45, node.getyPos() + 28, node.getNodeNE().getxPos() + 60, node.getNodeNE().getyPos());
                paint(g, node.getNodeNE());
        }
        if(node.getNodeSE()!= null){
                g.drawLine(node.getxPos() + 75, node.getyPos() + 28, node.getNodeSE().getxPos() + 60, node.getNodeSE().getyPos());
                paint(g, node.getNodeSE());
        }
        if(node.getNodeSW()!= null){
                g.drawLine(node.getxPos() + 105, node.getyPos() + 28, node.getNodeSW().getxPos() + 60, node.getNodeSW().getyPos());
                paint(g, node.getNodeSW());
        }
    }

    @Override
    public boolean checkLabel(String str) {
        return false;
    }

    @Override
    public boolean checkPoint(Point point) {
        return false;
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
            // TODO Auto-generated method stub
            PointQuadNode ans = findPointQuadNode(this.getRoot(), e);
            if(ans == null) return null;
            return ans;
    }

    @Override
    public void resetColor() {
    }

    @Override
    public void setColor(Node node) {
    }

    private void paintNode(Graphics2D g, PointQuadNode node) {
    	if(node == null){
    		return;
    	}
		
        g.setColor(Color.black);
		
    	int dx = 40;
    	int dy = 14;
    	
    	int x = node.getxPos();
		int y = node.getyPos();
		
    	g.drawString(node.getLabel(), x + 6 , y + dy - 1 );
    	g.drawString(String.valueOf(node.getxPos()), x + dx + 6, y + dy - 1);
    	g.drawString(String.valueOf(node.getyPos()), x + 2*dx + 6, y + dy - 1);
	    	
    	//========================================
    	
    	g.setColor(node.getColor());
    	
        g.drawRect(x + 4, y, dx, dy);
        g.drawRect(x + dx + 4, y, dx - 2, dy);
        g.drawRect(x + 2*dx + 2, y, dx - 2, dy);
        
        //=============================
        
        g.drawRect(x + 4, y + dy, 29, dy);
        g.drawRect(x + 33,y + dy, 29, dy);
        g.drawRect(x + 62, y + dy, 29, dy);
        g.drawRect(x + 91,y + dy, 29, dy );
        
        g.setColor(Color.black);
    }
}
