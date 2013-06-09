package com.mtbs3d.minecrift.hydra;

import java.io.File;

import com.sixense.ControllerData;
import com.sixense.Sixense;
import com.sixense.utils.ControllerManager;
import com.sixense.utils.ManagerCallback;
import com.sixense.utils.enums.EnumControllerDesc;
import com.sixense.utils.enums.EnumGameType;
import com.sixense.utils.enums.EnumSetupStep;

public class Test {

    public static ControllerData[] newData = {new ControllerData(), new ControllerData(), new ControllerData(), new ControllerData()};
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Sixense.LoadLibrary(new File("lib_path"));
		Sixense.init();
		final ControllerManager cm = ControllerManager.getInstance();
        cm.setGameType(EnumGameType.ONE_PLAYER_TWO_CONTROLLER);
		cm.registerSetupCallback(new ManagerCallback() {
			@Override
			public void onCallback(EnumSetupStep step) {
				System.out.println( "CB:"+ step.toString());
				
			}
		});
		boolean bContinue = true;
		while( bContinue )
		{
			Sixense.setActiveBase(0);
			Sixense.getAllNewestData(newData);
			System.out.println( Math.toDegrees(Math.acos(newData[0].rot_mat[2][2])));
			cm.update( newData );
			Thread.sleep(120);
			EnumSetupStep step =  cm.getCurrentStep() ;
			switch ( step )
			{
			case P1C2_IDLE:
				System.out.println("Idle...");
				bContinue = false;
				break;
			default:
				System.out.println( "default:"+step.toString() );
				System.out.println( cm.getTextureFileName() );
				break;
			}
		}
		
		if( Sixense.isBaseConnected(0) ) System.out.println("0 connected");
		else if( Sixense.isBaseConnected(1) ) System.out.println("1 connected");
		else if( Sixense.isBaseConnected(2) ) System.out.println("2 connected");
		else if( Sixense.isBaseConnected(3) ) System.out.println("3 connected");
		else System.out.println("None connected");
		
		Sixense.exit();
	}

}
