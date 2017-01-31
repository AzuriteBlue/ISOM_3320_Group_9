package com.isom.infrastructure.Util;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.isom.infrastructure.Screens.PlayScreen;
import sprite.*;

public class MyContactListener implements ContactListener {

    private Wiki wiki;
    private PlayScreen playScreen;

    public MyContactListener(PlayScreen playScreen, Wiki wiki) {
        this.playScreen = playScreen;
        this.wiki = wiki;
    }

    @Override
    public void beginContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // if wiki collides with something
        if (fixtureA.getUserData() instanceof Wiki || fixtureB.getUserData() instanceof Wiki) {

            // tell which from which
            Fixture wikiFix, otherFix;
            if (fixtureA.getUserData() instanceof Wiki) {
                wikiFix = fixtureA;
                otherFix = fixtureB;
            } else {
                wikiFix = fixtureB;
                otherFix = fixtureA;
            }


            // call onHit() of the other object
            if (otherFix.getUserData() instanceof Interactable) {
                ((Interactable) otherFix.getUserData()).onWikiHit(wiki);
            } else if (otherFix.getUserData() instanceof Sprite) {

            } else {
                wiki.boosted = false;
                wiki.jumped = false;
            }
        }





        // if a bullet collides with something
        if (fixtureA.getUserData() instanceof Bullet || fixtureB.getUserData() instanceof Bullet) {

            // tell which from which
            Fixture bulletFix, otherFix;
            if (fixtureA.getUserData() instanceof Bullet) {
                bulletFix = fixtureA;
                otherFix = fixtureB;
            } else {
                bulletFix = fixtureB;
                otherFix = fixtureA;
            }

            if (otherFix.getUserData() instanceof Wiki) {

            } else if (otherFix.getUserData() instanceof Enemy) {
                ((Enemy) otherFix.getUserData()).die();
            } else {
                Bullet bullet = (Bullet) (bulletFix.getUserData());

                bullet.destroyed = true;

                // remove reference
                if (playScreen.bullets.contains(bullet, true)) {
                    int index = playScreen.bullets.indexOf(bullet, true);
                    playScreen.bullets.removeIndex(index);
                }
            }
        }





        // if a bullet hits an enemy
        if ((fixtureA.getUserData() instanceof Enemy && fixtureB.getUserData() instanceof Bullet)
                || (fixtureB.getUserData() instanceof  Enemy && fixtureA.getUserData() instanceof Bullet)) {

            Fixture enemyFix = fixtureA.getUserData() instanceof Enemy ? fixtureA : fixtureB;
            ((Enemy)enemyFix.getUserData()).die();
        }

        if ((fixtureA.getUserData() instanceof Wiki && fixtureB.getUserData() instanceof Bullet)
                || (fixtureB.getUserData() instanceof  Wiki && fixtureA.getUserData() instanceof Bullet)) {

            Fixture wikiFix = fixtureA.getUserData() instanceof Wiki ? fixtureA : fixtureB;
            ((Wiki)wikiFix.getUserData()).die();
        }



    }


    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
