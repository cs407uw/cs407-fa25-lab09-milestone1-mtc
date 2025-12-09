package com.cs407.lab09

/**
 * Represents a ball that can move. (No Android UI imports!)
 *
 * Constructor parameters:
 * - backgroundWidth: the width of the background, of type Float
 * - backgroundHeight: the height of the background, of type Float
 * - ballSize: the width/height of the ball, of type Float
 */
class Ball(
    private val backgroundWidth: Float,
    private val backgroundHeight: Float,
    private val ballSize: Float
) {
    var posX = 0f
    var posY = 0f
    var velocityX = 0f
    var velocityY = 0f
    private var accX = 0f
    private var accY = 0f

    private var isFirstUpdate = true

    init {
        reset()
    }

    /**
     * Updates the ball's position and velocity based on the given acceleration and time step.
     * (See lab handout for physics equations)
     */
    fun updatePositionAndVelocity(xAcc: Float, yAcc: Float, dT: Float) {
        if(isFirstUpdate) {
            isFirstUpdate = false
            accX = xAcc
            accY = yAcc
            return
        }

        // Calculate new velocities (but don't update member variables yet)
        val newVelocityX = velocityX + 0.5f * (xAcc + accX) * dT
        val newVelocityY = velocityY + 0.5f * (yAcc + accY) * dT

        // Calculate distance traveled using OLD velocity
        val distanceX = velocityX * dT + (1f/6f) * dT * dT * (3f * accX + xAcc)
        val distanceY = velocityY * dT + (1f/6f) * dT * dT * (3f * accY + yAcc)

        // Update position
        posX += distanceX
        posY += distanceY

        // Update velocity to new calculated values
        velocityX = newVelocityX
        velocityY = newVelocityY

        // Store new acceleration for next frame
        accX = xAcc
        accY = yAcc

        // Check and handle boundary collisions
        checkBoundaries()
    }

    /**
     * Ensures the ball does not move outside the boundaries.
     * When it collides, velocity and acceleration perpendicular to the
     * boundary should be set to 0.
     */
    fun checkBoundaries() {
        // Left wall collision
        if (posX < 0) {
            posX = 0f
            velocityX = 0f
            accX = 0f
        }

        // Right wall collision
        if (posX + ballSize > backgroundWidth) {
            posX = backgroundWidth - ballSize
            velocityX = 0f
            accX = 0f
        }

        // Top wall collision
        if (posY < 0) {
            posY = 0f
            velocityY = 0f
            accY = 0f
        }

        // Bottom wall collision
        if (posY + ballSize > backgroundHeight) {
            posY = backgroundHeight - ballSize
            velocityY = 0f
            accY = 0f
        }
    }

    /**
     * Resets the ball to the center of the screen with zero
     * velocity and acceleration.
     */
    fun reset() {
        posX = (backgroundWidth - ballSize) / 2
        posY = (backgroundHeight - ballSize) / 2
        velocityX = 0f
        velocityY = 0f
        accX = 0f
        accY = 0f
        isFirstUpdate = true
    }
}