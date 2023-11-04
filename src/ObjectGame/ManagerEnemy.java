package ObjectGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class ManagerEnemy {
	
	
	// declare variable
	private List<Enemy> enemies = new CopyOnWriteArrayList<>();
	private Random random;
	private long lastSpawnTime = 0;
	private long Cooldown = 3000; // Cooldown duration in milliseconds (3 seconds)
	private boolean isSpawnTime = false;
	
	
	// Object SpaceShip
	private SpaceShip sp;
	
	
	
	// constructor
	public ManagerEnemy(SpaceShip sp) {
		
		random = new Random();
		this.sp = sp;
	}
	
	
	// method reset enemy
	
	public void reset() {
		this.enemies.clear();
	}
	
	
	// respawn enemy run away ...
	public void respawnEnemy() {
		
        long currentTime = System.currentTimeMillis();
        
        
        // check cooldown for spawn
        
        if (currentTime - lastSpawnTime >= Cooldown) {
        	
            lastSpawnTime = currentTime;
            
    		if(enemies.size() == 0) {	
    			for(int i = 0; i < random.nextInt(10); i++) {
    				enemies.add(new Enemy(random.nextInt(500), 65, (random.nextInt(4) + 1) , (random.nextInt(15) + 1)));
    			}
    			
    		}else {
    			enemies.add(new Enemy(random.nextInt(500), 65 , (random.nextInt(4) + 1) , (random.nextInt(15) + 1)));
    		}
    		
			System.out.println("Enemies Respawn ...");
        }
		
	}
	
	
	// move enemy to player
	
	public void moveAllEnemy() {
		
        if(enemies.size() == 0) return;
        
		for(int i = enemies.size() - 1; i >= 0  ;i--) {
			
			try {
				Enemy enemy = enemies.get(i);
				
				if(enemy != null) {
					enemy.setPositionY(enemy.getPositionY() + enemy.getSpeed());
					if(enemy.getPositionY() >= 550) {
						
						int rand = random.nextInt(4) + 1;
						this.sp.setScore(this.sp.getScore() - rand );
						this.sp.setHealth(this.sp.getHealth() - rand );
						this.sp.setColorAlertMessage(Color.red);
						this.sp.setAlertMessage("-" + rand + "HP");
						enemies.remove(i);
					}
				}
			} catch (ArrayIndexOutOfBoundsException err) {
				return;
			}


		}
	}
	
	
	//  hit enemy  player score pluse +++;
	
	public void hitEnemy() {
		  for (int i = this.sp.bullets.size() - 1; i >= 0; i--) {
			  
			  
					try {
			            	Bullet bullet = this.sp.bullets.get(i);
				            if(enemies.size() == 0) return;
				            
				            for (int j = enemies.size() - 1; j >= 0; j--) {
				                Enemy enemy = enemies.get(j);
				                
				                if(enemy != null) {
					               if (bullet.intersects(enemy)) {
					                	
					                    this.sp.bullets.remove(i);
					                    
					    				enemy.setHealth(enemy.getHealth() - 1);
					    				
					    				if(enemy.getHealth() <= 0) {
					    					enemies.remove(j);
						                    this.sp.setScore(this.sp.getScore() + 10);
						                    this.sp.setDestory_enemies(this.sp.getDestory_enemies() + 1);
			
					    				}
					               }
				                }
				                

				            }
						} catch (ArrayIndexOutOfBoundsException err)
					{
						return;
					}
		            

		  }

	}
	
	
	// enemy hit  player hp decrease
	
	public void enemyHitSpaceShip() {
		
          if(enemies.size() == 0) return;
          
		  for (int i = enemies.size() - 1; i >= 0; i--) {
	            Enemy enemy = enemies.get(i);
	            
	            if(enemy.intersects(this.sp)) {
                    this.sp.setScore(this.sp.getScore() - (random.nextInt(4) + 1));
                    this.sp.setHealth(this.sp.getHealth() - (random.nextInt(4) + 20));
                    enemies.remove(i);
					int rand = random.nextInt(10) + 1;
					this.sp.setColorAlertMessage(Color.red);
					this.sp.setAlertMessage("-" + rand + "HP");
	            }

	  }
	}
	
	
	// draw object 
	
	public void drawAllEnemy(Graphics g2) {
		
	    for (Enemy enemy : enemies) {
	    	enemy.draw(g2);
	    }
	    
	}
	

}