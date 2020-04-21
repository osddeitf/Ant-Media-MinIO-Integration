package com.amazonaws.services.s3.transfer;

import com.amazonaws.event.ProgressListener;

public interface Upload {

    void addProgressListener(ProgressListener listener);

    void waitForCompletion() throws Exception;
}
