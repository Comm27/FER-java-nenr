package hr.fer.zemris.fuzzy.ship;

import hr.fer.zemris.fuzzy.IDomain;
import hr.fer.zemris.fuzzy.SimpleDomain;

public class ShipDomains {

	public static final IDomain POSITION_DOMAIN;
	public static final IDomain STEERING_DOMAIN;
	public static final IDomain SPEED_DOMAIN;
	public static final IDomain ORIENTATION_DOMAIN;
	public static final IDomain ACCELERATION_DOMAIN;
	
	private static final int MIN_POSITION = 0;
	private static final int MAX_POSITION = 1300;
	
	private static final int MAX_RIGHT_STEER = -90;
	private static final int MAX_LEFT_STEER = 90;
	
	private static final int MIN_SPEED = 0;
	private static final int MAX_SPEED = 200;
	
	private static final int MIN_ORIENTATION = 0;
	private static final int MAX_ORIENTATION = 2;
	
	private static final int MIN_ACCELERATION = -50;
	private static final int MAX_ACCELERATION = 50;

	static {
		POSITION_DOMAIN = new SimpleDomain(MIN_POSITION, MAX_POSITION + 1);
		STEERING_DOMAIN = new SimpleDomain(MAX_RIGHT_STEER, MAX_LEFT_STEER + 1);
		SPEED_DOMAIN = new SimpleDomain(MIN_SPEED, MAX_SPEED);
		ORIENTATION_DOMAIN = new SimpleDomain(MIN_ORIENTATION, MAX_ORIENTATION);
		ACCELERATION_DOMAIN = new SimpleDomain(MIN_ACCELERATION, MAX_ACCELERATION);
	}
}
