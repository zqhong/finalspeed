// Copyright (c) 2015 D1SM.net

package net.fs.client;


import java.io.IOException;

import org.pcap4j.core.Pcaps;

import net.fs.rudp.Route;
import net.fs.utils.MLog;


public class ClientNoUI extends ClientUI{

	@Override
	void run(){
		systemName=System.getProperty("os.name").toLowerCase();
		MLog.info("System: "+systemName+" "+System.getProperty("os.version"));
		ui=this;

		checkQuanxian();
		loadConfig();

		model=new MapRuleListModel();
		tcpMapRuleListTable=new MapRuleListTable(this,model);
		


		checkFireWallOn();
		if(!success_firewall_windows){
			MLog.info("启动windows防火墙失败,请先运行防火墙服务.");
			System.exit(0);
		}
		if(!success_firewall_osx){
			MLog.info("启动ipfw/pf防火墙失败,请先安装.");
			System.exit(0);
		}
		
		Thread thread=new Thread(){
			public void run(){
				try {
					Pcaps.findAllDevs();
					b1=true;
				} catch (Exception e3) {
					e3.printStackTrace();
					
				}
			}
		};
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		//JOptionPane.showMessageDialog(mainFrame,System.getProperty("os.name"));
		if(!b1){
			try {
				String msg="启动失败,请先安装libpcap";
				if(systemName.contains("windows")){
					msg="启动失败,请先安装winpcap";
				}
				MLog.info(msg);
				if(systemName.contains("windows")){
					try {
						Runtime.getRuntime().exec("winpcap_install.exe",null);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.exit(0);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		
		try {
			mapClient=new MapClient(this);
		} catch (final Exception e1) {
			e1.printStackTrace();
			capException=e1;
			//System.exit(0);;
		}

		try {
			if(!mapClient.route_tcp.capEnv.tcpEnable){
				MLog.info("无可用网络接口,只能使用udp协议.");
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	
		mapClient.setUi(this);
		
		mapClient.setMapServer(config.getServerAddress(), config.getServerPort(),config.getRemotePort()	,null,null,config.isDirect_cn(),config.getProtocal().equals("tcp"),
				null);
		

		
		setSpeed(config.getDownloadSpeed(),config.getUploadSpeed());
		
		loadMapRule();
		
		if(config.getDownloadSpeed()==0||config.getUploadSpeed()==0){
			new SpeedSetFrame(ui,mainFrame);
		}
	
	}
	
	@Override
	public void setMessage(String message){}
	
	@Override
	void setSpeed(int downloadSpeed,int uploadSpeed){
		config.setDownloadSpeed(downloadSpeed);
		config.setUploadSpeed(uploadSpeed);
		Route.localDownloadSpeed=downloadSpeed;
		Route.localUploadSpeed=config.uploadSpeed;
	}
}

