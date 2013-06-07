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

import de.fruitfly.ovr.OculusRift;

/**
 * Implements head tracking interface of OculusRift, using
 * left Razer Hydra controller for head orientation (and position?). 
 * 
 * @author mabrowning
 *
 */
class Oculus extends OculusRift
{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getInitializationStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getPitchDegrees_LH() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getYawDegrees_LH() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRollDegrees_LH() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean init() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void poll() {
		// TODO Auto-generated method stub
		
	}
}