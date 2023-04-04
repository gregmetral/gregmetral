using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;
using UnityEngine.SceneManagement;

public class bird : MonoBehaviour
{
    [SerializeField] protected Rigidbody2D rb;
    [SerializeField] protected BoxCollider2D bc2D;
    protected float SPEED = 4f;
    [SerializeField] protected TextMeshPro tmp_score;
    [SerializeField] protected TextMeshPro tmp_over;
    [SerializeField] protected TextMeshPro tmp_final_distance;
    [SerializeField] protected TextMeshPro tmp_restart;
    [SerializeField] protected AudioSource endSound;
    [SerializeField] protected AudioSource deathSound;
    [SerializeField] protected AudioSource backgroundMusic;
    protected float timer = 2f;
    protected int distance;
    protected float state = 0;
    [HideInInspector] public static bool restart = false;
    [SerializeField] protected Animator ref_animator;



    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetKeyDown(KeyCode.Escape))
        {
            AsyncOperation asyncLoad = SceneManager.LoadSceneAsync("MainMenu");
        }
        if (state == 0)
        {
            restart = false;
            if (Input.GetKeyDown(KeyCode.UpArrow))
            {
                if (transform.position.y < 4.7)
                {
                    rb.velocity = new Vector2(0, SPEED);
                }
            }

            timer -= Time.deltaTime;
            if (timer < 0)
            {
                timer = 2f;
                distance++;
                tmp_score.SetText("Distance : " + distance + "m");
            }

            if (transform.position.y < -5.5)
            {
                StartCoroutine(EndGame());
            }
        }

        if (state == 1)
        {
            if (Input.GetKeyDown(KeyCode.Space))
            {
                state = 0;
                transform.position = new Vector3(-6.5f, 0, 0);
                ref_animator.SetTrigger("restart");
                bc2D.enabled = true;
                distance = 0;
                rb.velocity = new Vector2(0,0);
                tmp_final_distance.SetText("");
                tmp_restart.SetText("");
                tmp_over.SetText("");
                rb.velocity = new Vector2(0, 0);
                restart = true;
                timer = 2f;
                backgroundMusic.Play();
            }
        }

    }
    private void OnCollisionEnter2D(Collision2D collision)
    {
        StartCoroutine(EndGame());
    }

    protected IEnumerator EndGame()
    {
        backgroundMusic.Stop();
        state = 1;
        ref_animator.SetTrigger("dead");
        bc2D.enabled = false;
        tmp_over.SetText("Game Over");
        deathSound.Play();
        yield return new WaitForSeconds(2);
        tmp_final_distance.SetText("you did " + distance + "m");
        tmp_restart.SetText("Press the Space key to restart");
        endSound.Play();
    }


    public bool getRestart()
    {
        return restart;
    }
}
