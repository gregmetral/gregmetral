using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;

public class ball : MonoBehaviour
{
    [SerializeField]protected float SPEED = 6.0f;
    [SerializeField] protected Rigidbody2D rb;
    [SerializeField] protected TextMeshPro ref_tmp;
    [SerializeField] protected GameObject ref_paddle;
    [SerializeField] protected AudioSource bouncePaddleSound;
    [SerializeField] protected AudioSource bounceWallSound;
    [SerializeField] protected AudioSource IntroSound;
    [SerializeField] protected AudioSource pointWinSound;

    public static int score = 0;
    protected float timer = 3.0f;
    protected int state;

    // Start is called before the first frame update
    void Start()
    {
        state = 0; //not moving
        IntroSound.Play();
    }

    // Update is called once per frame
    void Update()
    {
        if (state == 0)
        {
            timer -= Time.deltaTime;
            if (timer < 0)
            {
                state = 1; //playing 
                rb.velocity = new Vector2(0, -SPEED);
            }
        }

        if (transform.position.y < -8)
        {
            state = 0;
            timer = 3.0f;
            UpdateText(-500);
            rb.velocity = new Vector2(0, 0);
            transform.position = new Vector3(0, -1.95f, 0);
            IntroSound.Play();

        }

        if (spawner.brick_count == 0)
        {
            rb.velocity = new Vector2(0, 0);
            transform.position = new Vector3(0, -1.95f, 0);
            state = 0;
            timer = 3.0f;
            IntroSound.Play();
        }

    }

    public void OnCollisionEnter2D(Collision2D collision)
    {
        if (collision.gameObject.tag == "bricktag")
        {
            UpdateText(50);
            bounceWallSound.Play();
        }
        if (collision.gameObject.tag == "padlletag")
        {
            float diffX = gameObject.transform.position.x - ref_paddle.transform.position.x;
            rb.velocity += new Vector2(diffX * 3, 0);
            bouncePaddleSound.Play();
        }
        if (collision.gameObject.tag == "walltag")
        {
            bounceWallSound.Play();
        }
        if (collision.gameObject.tag == "jesustag")
        {
            pointWinSound.Play();

        }
    }

    
    
    protected void UpdateText(int addscore)
    {
        if (score + addscore < 0)
        {
            score = 0;
        }else
        {
            score = score + addscore;
        }
        
        ref_tmp.SetText("score : " + score);
    }
}
