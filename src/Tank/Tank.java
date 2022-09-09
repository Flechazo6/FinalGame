package Tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public abstract class Tank extends GameObject{

    private boolean attackCoolDown =true;//������ȴ״̬
    private int attackCoolDownTime =1000;//������ȴʱ�������1000ms�����ӵ�
    //̹�����ַ���ͼƬ
    private String upImage; //�����ƶ�ʱ��ͼƬ
    private String downImage;//�����ƶ�ʱ��ͼƬ
    private String rightImage;//�����ƶ�ʱ��ͼƬ
    private String leftImage;//�����ƶ�ʱ��ͼƬ
    boolean alive = true;
    //̹�˳ߴ�
    int width = 40;
    int height = 50;
    //̹�˳�ʼ����
    Direction direction = Direction.UP;
    //̹���ٶ�
    private int speed = 3;
    //̹��ͷ������
    Point p;

    //̹�˹��캯��
    //̹�����꣬��Ϸ���棬ͼƬֱ�Ӽ̳���Ϸ����
    public Tank(String img, int x, int y, String upImage, String downImage, String leftImage, String rightImage, GamePanel gamePanel) {
        super(img, x, y, gamePanel);
//�ٸ��ĸ�����ͼƬ��ֵ
        this.upImage = upImage;
        this.leftImage = leftImage;
        this.downImage = downImage;
        this.rightImage = rightImage;
    }
//̹���ƶ�
    public void leftward(){
        direction = Direction.LEFT;
        setImg(leftImage);
        if(!hitWall(x-speed, y) && !moveToBorder(x-speed, y) && alive){
            this.x -= speed;
        }
    }
    public void rightward(){
        direction = Direction.RIGHT;
        setImg(rightImage);
        if(!hitWall(x+speed, y) && !moveToBorder(x+speed, y) && alive){
            this.x += speed;
        }
    }
    public void upward(){
        direction = Direction.UP;
        setImg(upImage);
        if(!hitWall(x, y-speed) && !moveToBorder(x, y- speed) && alive){
            this.y -= speed;
        }
    }
    public void downward(){
        direction = Direction.DOWN;
        setImg(downImage);
        if(!hitWall(x, y+speed) && !moveToBorder(x, y+speed) && alive){
            this.y += speed;
        }
    }

//���䷽��
    public void attack(){
        Point p = getHeadPoint();
        if(attackCoolDown && alive){
            Bullet bullet = new Bullet("images/bullet/bulletGreen.gif",p.x,p.y,direction, this.gamePanel);
            this.gamePanel.bulletList.add(bullet);
            attackCoolDown = false;
            new AttackCD().start();
        }
    }
//��Χǽ��ײ���
    public boolean hitWall(int x, int y){
        //�������̹��ǰ������һ��λ���γɵľ���
        Rectangle next = new Rectangle(x, y, width, height);
        //��ͼ�����е�ǽ��
        List<Wall> walls = this.gamePanel.wallList;
        //�ж����������Ƿ��ཻ�����Ƿ�ײǽ��
        for(Wall w:walls){
            if(w.getRec().intersects(next)){
                return true;
            }
        }
        return false;
    }

    public boolean moveToBorder(int x, int y){
        if(x < 0){
            return true;
        }else if(x > this.gamePanel.getWidth()-width){
            return true;
        }
        if(y < 0){
            return true;
        }else if(y > this.gamePanel.getHeight()-height){
            return true;
        }
        return false;
    }

    public class AttackCD extends Thread{
        public void run(){
            attackCoolDown=false;//��������������Ϊ��ȴ״̬
            try{
                Thread.sleep(attackCoolDownTime);//����1��
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            attackCoolDown=true;//���������ܽ����ȴ״̬
            this.stop();//��ֹ�߳�
        }
    }
//��ȡ̹��ͷ������
    //���ݷ���ȷ��ͷ��λ�ã�x��y�����½ǵĵ�
    public Point getHeadPoint(){
        switch (direction){
            case UP:
                return new Point(x + width/2, y );
            case LEFT:
                return new Point(x, y + height/2);
            case DOWN:
                return new Point(x + width/2, y + height);
            case RIGHT:
                return new Point(x + width, y + height/2);
            default:
                return null;
        }
    }

    @Override
    public void paintSelf(Graphics g) {
        g.drawImage(img, x, y, null);
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x, y, width, height);
    }
}
