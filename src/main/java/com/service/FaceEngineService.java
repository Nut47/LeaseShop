package com.service;

import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.toolkit.ImageInfo;
import com.vo.FaceUserInfo;
import com.vo.ProcessInfo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface FaceEngineService {

    List<FaceInfo> detectFaces(ImageInfo imageInfo);

    List<ProcessInfo> process(ImageInfo imageInfo);

    byte[] extractFaceFeature(ImageInfo imageInfo) throws InterruptedException;

    List<FaceUserInfo> compareFaceFeature(byte[] faceFeature, Integer groupId) throws InterruptedException, ExecutionException;



}
