package com.isom.infrastructure.Util;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
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
//                wiki.boosted = false;
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


            ((Bullet) bulletFix.getUserData()).destroy();

            if (otherFix.getUserData() instanceof Wiki) {
                ((Wiki) otherFix.getUserData()).die();

            } else if (otherFix.getUserData() instanceof Enemy) {
                ((Enemy) otherFix.getUserData()).die();

            } else if (otherFix.getUserData() instanceof Bullet) {
                ((Bullet) otherFix.getUserData()).destroy();
            }
        }


        //only collide with the ground or wiki
//        if ((fixtureA.getUserData() instanceof Sentry && (fixtureB.getUserData() instanceof Wiki))
//            || (fixtureB.getUserData() instanceof Sentry && (fixtureB.getUserData() instanceof Wiki))) {
//            Fixture sentryFix = fixtureA.getUserData() instanceof Sentry ? fixtureA : fixtureB;
//            sentryFix.getBody().setType(BodyDef.BodyType.StaticBody);
//        }



        // if a bullet hits an enemy
//        if ((fixtureA.getUserData() instanceof Enemy && fixtureB.getUserData() instanceof Bullet)
//                || (fixtureB.getUserData() instanceof  Enemy && fixtureA.getUserData() instanceof Bullet)) {
//
//            Fixture enemyFix = fixtureA.getUserData() instanceof Enemy ? fixtureA : fixtureB;
//            Fixture bulletFix = fixtureA.getUserData() instanceof Bullet ? fixtureA : fixtureB;
//            ((Enemy)enemyFix.getUserData()).die();
//            ((Bullet)bulletFix.getUserData()).destroy();
//        }

//        if ((fixtureA.getUserData() instanceof Wiki && fixtureB.getUserData() instanceof Bullet)
//                || (fixtureB.getUserData() instanceof  Wiki && fixtureA.getUserData() instanceof Bullet)) {
//
//            Fixture wikiFix = fixtureA.getUserData() instanceof Wiki ? fixtureA : fixtureB;
//            Fixture bulletFix = fixtureA.getUserData() instanceof Bullet ? fixtureA : fixtureB;
//            ((Wiki)wikiFix.getUserData()).die();
//            ((Bullet)bulletFix.getUserData()).destroy();
//        }



    }


    @Override
    public void endContact(Contact contact) {
//        Fixture fixtureA = contact.getFixtureA();
//        Fixture fixtureB = contact.getFixtureB();
//
//        if ((fixtureA.getUserData() instanceof Ladder && fixtureB.getUserData() instanceof Wiki)
//                || (fixtureB.getUserData() instanceof  Ladder && fixtureA.getUserData() instanceof Wiki))
//            wiki.body.applyLinearImpulse(new Vector2(3f,0), wiki.body.getWorldCenter(), true);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
