/*
 * (C) Copyright 2013 Mark Browning
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 3 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 */


package com.mtbs3d.minecrift.hydra;

import java.io.File;

import de.fruitfly.ovr.OculusRift;

import com.sixense.ControllerData;
import com.sixense.utils.ControllerManager;
import com.sixense.utils.ManagerCallback;
import com.sixense.utils.enums.EnumGameType;
import com.sixense.utils.enums.EnumSetupStep;
import com.sixense.Sixense;

/**
 * Implements head tracking interface of OculusRift, using
 * left Razer Hydra controller for head orientation (and position?). 
 * 
 * @author mabrowning
 *
 */
public class Oculus extends OculusRift implements ManagerCallback
{
    public ControllerData[] newestData = {new ControllerData(), new ControllerData(), new ControllerData(), new ControllerData()};
    ControllerManager cm;

	boolean initialized = false;
	boolean cmSetup = false;
	
	float pitch, yaw, roll;

	@Override
	public boolean init( File nativeDir ) {
		Oculus.LoadLibrary(nativeDir);
		if( Sixense.LoadLibrary( nativeDir ) )
		{
			if(Sixense.init())
			{
				cm = ControllerManager.getInstance();
				cm.setGameType(EnumGameType.ONE_PLAYER_TWO_CONTROLLER);
				cm.registerSetupCallback(this);
				Sixense.getAllNewestData(newestData);

				this._initSummary = "Initializing Sixense Controller Manager...";
				cm.update(newestData);
				cmSetup	= true;
			}
			else
			{
				this._initSummary = "Couldn't initialize Sixense Library";
			}
		}
		else
		{
			this._initSummary = "Couldn't load library";
		}
		System.out.println("Hydra: "+ _initSummary);
		return initialized;
	}

	@Override
	public void destroy() {
		Sixense.exit();
		
	}

	@Override
	public String getInitializationStatus() {
		return this._initSummary;
	}

	@Override
	public float getPitchDegrees_LH() {
		return pitch;
	}

	@Override
	public float getYawDegrees_LH() {
		return yaw;
	}

	@Override
	public float getRollDegrees_LH() {
		return roll;
	}


	@Override
	public String getVersion() {
		return "0.1";
	}


	@Override
	public boolean isInitialized() {
		if( !initialized && cmSetup )
		{
			Sixense.getAllNewestData(newestData);
			System.out.println(this._initSummary);
			cm.update(newestData);
		}
		return initialized;
	}

	@Override
	public void poll() {
		Sixense.getAllNewestData(newestData);

		/*
		float[][] m = newestData[0].rot_mat;
		
		double theta1 = Math.atan2(m[1][2], m[2][2]);
		double c2 = Math.sqrt(m[0][0]*m[0][0] + m[0][1]*m[0][1]);
		double theta2 = Math.atan2(-m[0][2], c2);
		double s1 = Math.sin(theta1); double c1 = Math.cos(theta1);
		double theta3 = Math.atan2(s1*m[2][0] - c1*m[1][0], c1*m[1][1] - s1*m[2][1]);
		
		pitch = (float)Math.toDegrees(theta1);
		yaw   = (float)Math.toDegrees(theta2); 
		roll  = (float)Math.toDegrees(theta3);
		//acos(mat_3,3)
		//pitch = (float)Math.toDegrees( Math.acos(newestData[0].rot_mat[2][2]));
		
		//atan2(mat_3,1 , mat_3,2)
		//yaw = (float) Math.toDegrees(Math.atan2(newestData[0].rot_mat[2][0], newestData[0].rot_mat[2][1]));

		//atan2(mat_1,3 , mat_2,3)
		//roll = (float) Math.toDegrees(Math.atan2(newestData[0].rot_mat[0][2], newestData[0].rot_mat[1][2]));
		 */
		float[] Q = newestData[0].rot_quat;
		int W = 3;
		int A1 = 0; //x
		int A2 = 2; //z
		int A3 = 1; //y

		int D = -1; //CW
		int S = 1; //Right handed
		float ww = Q[W]*Q[W];
		float Q11 = Q[A1]*Q[A1];
		float Q22 = Q[A2]*Q[A2];
		float Q33 = Q[A3]*Q[A3];
		int psign = -1;
		if(((A1+1)%3 ==A2) && ((A2+1)%3 == A3)) 
			psign = 1;

		double a,b,c,w = Q[W];
		double s2 = psign*Q[A2]*(psign*w*Q[A2] + Q[A1]*Q[A3]);
        if (s2 < -0.5)
        { // South pole singularity
            a = 0.0;
            b = -S*D*Math.PI/2;
            c = S*D*Math.atan2(2.0*(psign*Q[A1]*Q[A2] + w*Q[A3]),
		                   ww + Q22 - Q11 - Q33 );
        }
        else if (s2 > 0.5)
        {  // North pole singularity
            a = 0.0;
            b = S*D*Math.PI/2;
            c = S*D*Math.atan2(2.0*(psign*Q[A1]*Q[A2] + w*Q[A3]),
		                   ww + Q22 - Q11 - Q33);
        }
        else
        {
            a = -S*D*Math.atan2(-2.0*(w*Q[A1] - psign*Q[A2]*Q[A3]),
		                    ww + Q33 - Q11 - Q22);
            b = S*D*Math.asin(s2);
            c = S*D*Math.atan2(2.0*(w*Q[A3] - psign*Q[A1]*Q[A2]),
		                   ww + Q11 - Q22 - Q33);
        }    

		yaw   = (float)Math.toDegrees(c);
		pitch = (float)Math.toDegrees(a);
		roll  = (float)Math.toDegrees(b);
	}


	/**
	 * Called by the Sixense Controller manager when the setup step changes
	 */
	@Override
	public void onCallback(EnumSetupStep step) {
		switch(step)
		{
		case P1C2_DONE:
		case P1C2_IDLE:
		case SETUP_COMPLETE:
			this._initSummary = "Razer Hydra initialized";
			initialized = true;
			break;
		default:
			//Shows the "help" for the current step
			this._initSummary = cm.getStepString();
		}
	}
}