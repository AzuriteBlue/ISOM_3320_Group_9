package com.isom.infrastructure.Util;


import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.isom.infrastructure.Screens.PlayScreen;
import sprite.Battery;
import sprite.Bullet;
import sprite.Enemy;
import sprite.Wiki;

public class MyContactFilter implements ContactFilter {

    private Wiki wiki;
    private PlayScreen playScreen;


    public MyContactFilter(PlayScreen playScreen, Wiki wiki) {
        this.playScreen = playScreen;
        this.wiki = wiki;
    }
    @Override
    public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {

        // destroyed bullet
        if (fixtureA.getUserData() instanceof Bullet || fixtureB.getUserData() instanceof Bullet) {
            // tell which from which
            Fixture bulletFix = fixtureA.getUserData() instanceof Bullet ? fixtureA : fixtureB;
            if (((Bullet)bulletFix.getUserData()).destroyed)
                return false;
        }

        // collected battery
        if ((fixtureA.getUserData() instanceof Battery && fixtureB.getUserData() instanceof Wiki)
                || (fixtureB.getUserData() instanceof  Battery && fixtureA.getUserData() instanceof Wiki)) {
            // tell which from which
            Fixture batteryFix = fixtureA.getUserData() instanceof Battery ? fixtureA : fixtureB;
            ((Battery)batteryFix.getUserData()).collectBy(wiki);
            return false;
        }

        // dead enemy
        if (fixtureA.getUserData() instanceof Enemy || fixtureB.getUserData() instanceof Enemy) {
            // tell which from which
            Fixture enemyFix = fixtureA.getUserData() instanceof Enemy ? fixtureA : fixtureB;
            if (((Enemy)enemyFix.getUserData()).isDead)
                return false;
        }




        return true;
    }
}
