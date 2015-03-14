package de.tu_darmstadt.gdi1.gorillas.mapobjects;



/**
 * Class FlyingObject
 */
public class FlyingObject extends MapObject {

		public FlyingObject(String entityID) {
		super(entityID);
		// TODO Auto-generated constructor stub
	}

		//
		// Fields
		//

		private double angle;
		private int x;
		private int y;
		private double velocity;
		private double velocityX;
		private double velocityY;
		private int xZero;
		private int yZero;
		private double gravity;
		private int timeZero;
		private int windScale;
		private double dampingFactor;
		
		//
		// Methods
		//


		//
		// Accessor methods
		//

		/**
		 * Set the value of angle
		 * @param newVar the new value of angle
		 */
		private void setAngle (double newVar) {
				angle = newVar;
		}

		/**
		 * Get the value of angle
		 * @return the value of angle
		 */
		private double getAngle () {
				return angle;
		}

		/**
		 * Set the value of x
		 * @param newVar the new value of x
		 */
		private void setX (int newVar) {
				x = newVar;
		}

		/**
		 * Get the value of x
		 * @return the value of x
		 */
		private int getX () {
				return x;
		}

		/**
		 * Set the value of y
		 * @param newVar the new value of y
		 */
		private void setY (int newVar) {
				y = newVar;
		}

		/**
		 * Get the value of y
		 * @return the value of y
		 */
		private int getY () {
				return y;
		}

		/**
		 * Set the value of velocity
		 * @param newVar the new value of velocity
		 */
		private void setVelocity (double newVar) {
				velocity = newVar;
		}

		/**
		 * Get the value of velocity
		 * @return the value of velocity
		 */
		private double getVelocity () {
				return velocity;
		}

		/**
		 * Set the value of velocityX
		 * @param newVar the new value of velocityX
		 */
		private void setVelocityX (double newVar) {
				velocityX = newVar;
		}

		/**
		 * Get the value of velocityX
		 * @return the value of velocityX
		 */
		private double getVelocityX () {
				return velocityX;
		}

		/**
		 * Set the value of velocityY
		 * @param newVar the new value of velocityY
		 */
		private void setVelocityY (double newVar) {
				velocityY = newVar;
		}

		/**
		 * Get the value of velocityY
		 * @return the value of velocityY
		 */
		private double getVelocityY () {
				return velocityY;
		}

		/**
		 * Set the value of xZero
		 * @param newVar the new value of xZero
		 */
		private void setXZero (int newVar) {
				xZero = newVar;
		}

		/**
		 * Get the value of xZero
		 * @return the value of xZero
		 */
		private int getXZero () {
				return xZero;
		}

		/**
		 * Set the value of yZero
		 * @param newVar the new value of yZero
		 */
		private void setYZero (int newVar) {
				yZero = newVar;
		}

		/**
		 * Get the value of yZero
		 * @return the value of yZero
		 */
		private int getYZero () {
				return yZero;
		}

		/**
		 * Set the value of gravity
		 * @param newVar the new value of gravity
		 */
		private void setGravity (double newVar) {
				gravity = newVar;
		}

		/**
		 * Get the value of gravity
		 * @return the value of gravity
		 */
		private double getGravity () {
				return gravity;
		}

		/**
		 * Set the value of timeZero
		 * @param newVar the new value of timeZero
		 */
		private void setTimeZero (int newVar) {
				timeZero = newVar;
		}

		/**
		 * Get the value of timeZero
		 * @return the value of timeZero
		 */
		private int getTimeZero () {
				return timeZero;
		}

		/**
		 * Set the value of windScale
		 * @param newVar the new value of windScale
		 */
		private void setWindScale (int newVar) {
				windScale = newVar;
		}

		/**
		 * Get the value of windScale
		 * @return the value of windScale
		 */
		private int getWindScale () {
				return windScale;
		}

		/**
		 * Set the value of dampingFactor
		 * @param newVar the new value of dampingFactor
		 */
		private void setDampingFactor (double newVar) {
				dampingFactor = newVar;
		}

		/**
		 * Get the value of dampingFactor
		 * @return the value of dampingFactor
		 */
		private double getDampingFactor () {
				return dampingFactor;
		}

		//
		// Other methods
		//

}
