package io;

import java.io.*;
import java.nio.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by kanyuxia on 2017/3/4.
 */
public class SutdyNIO {
    public static void main(String[] args) throws IOException, InterruptedException {
//        getChannel();
//        channelCopy();
//        transferTo();
//        getData();
//        viewBuffers();
//        bufferOrder();
//        usingBuffer();
        fileLock();
    }
    private static void getChannel() throws IOException{
        FileChannel fc = new FileOutputStream("E:\\java\\word.txt").getChannel();
        fc.write(ByteBuffer.wrap("append some words".getBytes()));
        fc.close();

        fc = new RandomAccessFile("E:\\java\\word.txt","rw").getChannel();
        fc.position(fc.size());
        fc.write(ByteBuffer.wrap("\nappend some words too".getBytes()));
        fc.close();

        fc = new FileInputStream("E:\\java\\word.txt").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        fc.read(buffer);
        buffer.flip();
        while(buffer.hasRemaining()){
            System.out.print((char)buffer.get());
        }
    }

    private static void channelCopy() throws IOException{
        FileChannel in = new FileInputStream("E:\\java\\word.txt").getChannel();
        FileChannel out = new FileOutputStream("E:\\java\\copyword.txt").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while(in.read(buffer) != -1){
            buffer.flip();  // Prepare for writing
            while(buffer.hasRemaining()){
                System.out.print((char) buffer.get());
            }
            out.write(buffer);
            while(buffer.hasRemaining()){
                System.out.print( "1" + (char) buffer.get());
            }
            buffer.clear();  // Prepare for reading
        }
    }

    private static void transferTo() throws IOException{
        FileChannel in = new FileInputStream("E:\\java\\word.txt").getChannel();
        FileChannel out = new FileOutputStream("E:\\java\\copyword.txt").getChannel();
        in.transferTo(0,in.size(),out);
    }

    private static void getData() throws IOException{
        FileChannel in = new FileInputStream("E:\\java\\word.txt").getChannel();
        FileChannel out = new FileOutputStream("E:\\java\\word.txt").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.asCharBuffer().put("adCharBuffer");
        out.write(buffer);
        out.close();

        in.read(buffer);
        buffer.flip();
        System.out.println(buffer.asCharBuffer());
    }

    private static void viewBuffers(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put(new byte[]{0,1,2,3,4,'a'});
        System.out.println("Byte Buffer");
        byteBuffer.rewind();
        while(byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.position() + "-->" + byteBuffer.get());
        }

        System.out.println("Char Buffer");
        byteBuffer.rewind();
        CharBuffer charBuffer = byteBuffer.asCharBuffer();
        while(charBuffer.hasRemaining()){
            System.out.println(charBuffer.position() + "--->" + charBuffer.get());
        }

        System.out.println("Short Buffer");
        byteBuffer.rewind();
        ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
        while(shortBuffer.hasRemaining()){
            System.out.println(shortBuffer.position() + "--->" + shortBuffer.get());
        }

        System.out.println("Int Buffer");
        byteBuffer.rewind();
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        while(intBuffer.hasRemaining()){
            System.out.println(intBuffer.position() + "--->" + intBuffer.get());
        }

        System.out.println("Float Buffer");
        byteBuffer.rewind();
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        while(floatBuffer.hasRemaining()){
            System.out.println(floatBuffer.position() + "--->" + floatBuffer.get());
        }

        System.out.println("Long Buffer");
        byteBuffer.rewind();
        LongBuffer longBuffer = byteBuffer.asLongBuffer();
        while(longBuffer.hasRemaining()){
            System.out.println(longBuffer.position() + "--->" + longBuffer.get());
        }

        System.out.println("Double Buffer");
        byteBuffer.rewind();
        DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();
        while(doubleBuffer.hasRemaining()){
            System.out.println(doubleBuffer.position() + "--->" + doubleBuffer.get());
        }
    }

    private static void bufferOrder(){
        ByteBuffer buffer = ByteBuffer.wrap(new byte[12]);
        buffer.asCharBuffer().put("abcdef");
        System.out.println(Arrays.toString(buffer.array()));

        buffer.rewind();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.asCharBuffer().put("abcdef");
        System.out.println(Arrays.toString(buffer.array()));

        buffer.rewind();
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.asCharBuffer().put("abcdef");
        System.out.println(Arrays.toString(buffer.array()));
    }

    private static void usingBuffer(){
        char[] chars = "Tencen".toCharArray();
        ByteBuffer byteBuffer = ByteBuffer.allocate(chars.length * 2);
        CharBuffer charBuffer = byteBuffer.asCharBuffer();
        charBuffer.put(chars);

        System.out.print("swap before：");
        charBuffer.rewind();
        System.out.println(charBuffer);

        while(charBuffer.hasRemaining()){
            charBuffer.mark();
            char c1 = charBuffer.get();
            char c2 = charBuffer.get();
            charBuffer.reset();
            charBuffer.put(c2);
            charBuffer.put(c1);
        }

        System.out.print("swap after：");
        charBuffer.rewind();
        System.out.println(charBuffer);
    }

    private static void fileLock() throws IOException, InterruptedException {
        FileChannel fileChannel = new FileOutputStream("E:\\java\\People.java").getChannel();
        // lock()是阻塞的：如果没有加成锁，则线程等待直至加锁完成。
//        FileLock fileLock = fileChannel.lock();
        // tryLock()是非阻塞的：没有加成锁，则返回null。
        FileLock fileLock = fileChannel.tryLock();
        if(fileLock != null){
            System.out.println("File Locked");
            TimeUnit.MILLISECONDS.sleep(10000);
            fileLock.release();
            System.out.println("File Released");
        }
        fileChannel.close();
    }

}
